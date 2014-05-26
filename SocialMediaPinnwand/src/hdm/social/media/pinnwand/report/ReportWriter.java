package hdm.social.media.pinnwand.report;

/**
 * <p>
 * Diese Klasse wird benötigt, um auf dem Client die ihm vom Server zur
 * Verfügung gestellten <code>Report</code>-Objekte in ein menschenlesbares
 * Format zu überführen.
 * </p>
 * <p>
 * Das Zielformat kann prinzipiell beliebig sein. Methoden zum Auslesen der in
 * das Zielformat überführten Information wird den Subklassen überlassen. In
 * dieser Klasse werden die Signaturen der Methoden deklariert, die für die
 * Prozessierung der Quellinformation zuständig sind.
 * </p>
 * 
 * @author Thies
 */
public abstract class ReportWriter {

  /**
   * Übersetzen eines <code>AllAccountsOfCustomerReport</code> in das
   * Zielformat.
   * 
   * @param r der zu übersetzende Report
   */
  public abstract void process(AllAccountsOfCustomerReport r);

  /**
   * Übersetzen eines <code>AllAccountsOfAllCustomersReport</code> in das
   * Zielformat.
   * 
   * @param r der zu übersetzende Report
   */
  public abstract void process(AllAccountsOfAllCustomersReport r);

}
