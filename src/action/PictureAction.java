package action;

import domain.Picture;
import domain.PictureWithBLOBs;
import global.Constants;
import global.beanUtils.BeanUtils;
import global.security.SessionUtils;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import service.PictureService;
import util.FileUtils;
import util.MessageUtils;
import util.ServletHelp;
import util.StringUtils;

public class PictureAction extends BaseAction
{
  private PictureService pictureService = null;

  public void setPictureService(PictureService pictureService)
  {
    this.pictureService = pictureService;
  }

  public ActionForward queryForPaging(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map map = request.getParameterMap();
    Picture paramPicture = new Picture();
    BeanUtils.populate(paramPicture, map);

    JSONArray datas = new JSONArray();
    List pictureList = this.pictureService
      .selectByCriteriaForPaging(paramPicture);
    if (pictureList != null)
    {
      datas = JSONArray.fromObject(pictureList);
    }
    int count = this.pictureService.countByCriteria(paramPicture);

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    res.put("datas", datas);
    res.put("results", Integer.valueOf(count));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String pictureIds = request.getParameter("pictureIds");

    String pictureNames = request.getParameter("pictureNames");

    this.pictureService.update(pictureIds, pictureNames);

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward move(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String pictureIds = request.getParameter("pictureIds");

    String albumId = request.getParameter("albumId");

    this.pictureService.move(pictureIds, albumId);

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String pictureIds = request.getParameter("pictureIds");
    String uuids = request.getParameter("uuids");
    if ((StringUtils.isNotEmpty(pictureIds)) && (StringUtils.isNotEmpty(uuids)))
    {
      String uploadFilePath = ServletHelp.getRealPath(request, 
        MessageUtils.setParamMessage("/websrc/file/{0}/picture", new String[] { SessionUtils.getCurrentUserId() }));
      this.pictureService.delete(pictureIds, uuids, uploadFilePath);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String userId = request.getParameter("userId");

    String albumId = request.getParameter("albumId");

    String uploadFilePath = ServletHelp.getRealPath(request, 
      MessageUtils.setParamMessage("/websrc/file/{0}/picture", new String[] { SessionUtils.getCurrentUserId() }));
    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);
    List items = null;

    items = upload.parseRequest(request);
    if ((StringUtils.isNotEmpty(albumId)) && (items != null)) {
      Iterator it = items.iterator();
      while (it.hasNext())
      {
        FileItem fi = (FileItem)it.next();
        if ("fileData".equals(fi.getFieldName())) {
          String fileUUID = FileUtils.getUUID();
          String fileType = FileUtils.getFileTypeByName(fi.getName());
          String lpath = uploadFilePath + "/" + fileUUID + "." + fileType;
          String spath = uploadFilePath + "/" + "thumbnail" + "/" + fileUUID + "_s." + fileType;

          FileUtils.writeFileToDisk(fi.get(), lpath);

          Thumbnails.of(new File[] { new File(lpath) }).size(Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT).toFile(new File(spath));

          PictureWithBLOBs picture = new PictureWithBLOBs();
          picture.setPictureName(FileUtils.getFileName(fi.getName()));
          picture.setType(FileUtils.getFileTypeByName(fi.getName()));
          picture.setLdata(null);
          picture.setLpath(fileUUID + "." + fileType);
          picture.setSdata(null);
          picture.setSpath(fileUUID + "_s." + fileType);
          picture.setAlbumId(Integer.valueOf(Integer.parseInt(albumId)));
          picture.setStatus("1");
          picture.setDelflag("1");
          picture.setCreateUser(userId);
          picture.setUpdateUser(userId);

          this.pictureService.insert(picture);
        }
      }

    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward grabWebPic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String albumId = request.getParameter("albumId");

    String picUrls = request.getParameter("picUrls");

    if ((StringUtils.isNotEmpty(albumId)) && (StringUtils.isNotEmpty(picUrls)))
    {
      String uploadFilePath = ServletHelp.getRealPath(request, 
        MessageUtils.setParamMessage("/websrc/file/{0}/picture", new String[] { SessionUtils.getCurrentUserId() }));
      String[] picUrlArr = picUrls.split(",");
      for (String picUrl : picUrlArr)
      {
        String fileName = picUrl.substring(picUrl.lastIndexOf("/") + 1);

        String fileUUID = FileUtils.getUUID();
        String fileType = FileUtils.getFileTypeByName(fileName);
        String lpath = uploadFilePath + "/" + fileUUID + "." + fileType;
        String spath = uploadFilePath + "/" + "thumbnail" + "/" + fileUUID + "_s." + fileType;

        FileUtils.saveFileByUrl(picUrl, lpath);

        Thumbnails.of(new File[] { new File(lpath) }).size(Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT).toFile(new File(spath));

        PictureWithBLOBs picture = new PictureWithBLOBs();
        picture.setPictureName(FileUtils.getFileName(fileName));
        picture.setType(FileUtils.getFileTypeByName(fileName));
        picture.setLdata(null);
        picture.setLpath(fileUUID + "." + fileType);
        picture.setSdata(null);
        picture.setSpath(fileUUID + "_s." + fileType);
        picture.setAlbumId(Integer.valueOf(Integer.parseInt(albumId)));
        picture.setStatus("1");
        picture.setDelflag("1");

        this.pictureService.insert(picture);
      }

    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }
}