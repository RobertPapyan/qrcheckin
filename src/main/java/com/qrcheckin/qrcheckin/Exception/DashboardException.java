package com.qrcheckin.qrcheckin.Exception;

public class DashboardException extends RuntimeException {
  private final String displayMessage;

  public DashboardException(String message, String displayMessage) {
    super(message);
    this.displayMessage = displayMessage;
  }

  public String getDisplayMessage() {
    return displayMessage;
  }

  @Override
  public String toString() {
    return "DashboardException." + "\n"
            + "Display message: " + this.getDisplayMessage() + "\n"
            + "Message: " + this.getMessage();
  }
}
