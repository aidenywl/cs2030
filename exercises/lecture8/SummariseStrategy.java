@FunctionalInterface
interface SummaryStrategy {
  String summarize(String text, int lengthLimit);
}