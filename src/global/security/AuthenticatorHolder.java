package global.security;

public class AuthenticatorHolder
{
  static ThreadLocal<ClientSession> clientSessionList = new ThreadLocal();

  public static void setClientSession(ClientSession clientSession) {
    if (clientSessionList == null) {
      clientSessionList = new ThreadLocal();
    }
    clientSessionList.set(clientSession);
  }

  public static ClientSession getClientSession() {
    if (clientSessionList == null) {
      clientSessionList = new ThreadLocal();
    }
    return (ClientSession)clientSessionList.get();
  }

  public static void closeClientSession() {
    clientSessionList.set(null);
    clientSessionList = null;
  }
}