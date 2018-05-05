enum Days {
  MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

// this is actually

/*

public final class Days extends Enum<Days> {
  public static final Days[] values { .. }
  public static Days valueOf(String name) { .. }

  public static final Days MONDAY;
  public static final Days TUESDAY;
  :
  static {

}
*/
