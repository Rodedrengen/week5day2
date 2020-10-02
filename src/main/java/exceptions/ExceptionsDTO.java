package exceptions;

public class ExceptionsDTO{

  public ExceptionsDTO(int code, String description){
      this.code = code;
      this.message = description;
  }
  private int code;
  private String message;
}
