package domain;

public class PictureWithBLOBs extends Picture
{
  private static final long serialVersionUID = -3931698011216252677L;
  private byte[] ldata;
  private byte[] sdata;

  public byte[] getLdata()
  {
    return this.ldata;
  }

  public void setLdata(byte[] ldata)
  {
    this.ldata = ldata;
  }

  public byte[] getSdata()
  {
    return this.sdata;
  }

  public void setSdata(byte[] sdata)
  {
    this.sdata = sdata;
  }
}