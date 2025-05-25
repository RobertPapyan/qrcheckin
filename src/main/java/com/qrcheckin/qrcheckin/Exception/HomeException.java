package com.qrcheckin.qrcheckin.Exception;

public class HomeException extends DashboardException {


  public HomeException(String message, String displayMessage) {
    super(message, displayMessage);
  }

  public HomeException(String message, String displayMessage, Throwable cause) {
    super(message, displayMessage, cause);
  }
}
