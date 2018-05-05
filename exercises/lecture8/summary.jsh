@FunctionalInterface
interface SummaryStrategy {
  String summarize(String text, int lengthLimit);
}

void createSnippet(SummaryStrategy strategy) {
  
}

createSnippet(TextShortener::shorten);

Double d = foo(i);
String s = bar(d);

can be written as

stream.map(i -> foo(i)).map(d -> bar(d));

or

stream.map(i -> bar(foo(i)));

