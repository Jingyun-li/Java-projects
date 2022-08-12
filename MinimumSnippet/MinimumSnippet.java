import java.util.*;

public class MinimumSnippet {

	Iterable<String> document;
	List<String> terms;
	public int length = Integer.MAX_VALUE;
	public int startingPos = 0;
	public int endingPos = 0;
	ArrayList<Term> termsContained;
	ArrayList<Snippet> snippets;

	public MinimumSnippet(Iterable<String> document, List<String> terms) {
		this.document = document;
		this.terms = terms;
		if (terms.isEmpty()) {
			throw new IllegalArgumentException();
		}
		termsContained = new ArrayList<Term>();
		int index = 0;
		for (String d : document) {
			if (terms.contains(d)) {
				termsContained.add(new Term(index, d));
			}
			index++;
		}
		int termLength = terms.size();
		int listLength = termsContained.size();
		ArrayList<Snippet> snippets = new ArrayList<Snippet>();
		for (int i = 0; i < listLength - termLength + 1; i++) {
			snippets.add(new Snippet(i, i + termLength - 1));
		}
		for (int i = 0; i < snippets.size(); i++) {
			Snippet s = snippets.get(i);
			if (!containsAll(s)) {
				snippets.remove(s);
				i--;
			}
		}
		int currLength = 0;
		for (Snippet s : snippets) {
			if (currLength <= length) {
				startingPos = termsContained.get(s.getStartingTLPos()).getIndex();
				endingPos = termsContained.get(s.getEndingTLPos()).getIndex();
				currLength = endingPos - startingPos + 1;
				if (currLength < length) {
					length = currLength;
				}
			}
		}
	}

	public boolean containsAll(Snippet s) {
		ArrayList<String> currTerms = new ArrayList<String>();
		int start = s.getStartingTLPos();
		int ending = s.getEndingTLPos();
		for (int i = start; i <= ending; i++) {
			Term curTerm = termsContained.get(i);
			String term = curTerm.getTerm();
			currTerms.add(term);
		}
		if (currTerms.containsAll(terms)) {
			return true;
		}
		return false;
	}

	public boolean foundAllTerms() {
		ArrayList<String> terms2 = new ArrayList<String>();
		for (String term : terms) {
			terms2.add(term);
		}
		for (String term : terms) {
			int i = 0;
			while (i < termsContained.size()) {
				if (termsContained.get(i).getTerm().equals(term)) {
					terms2.remove(term);
				}
				i++;
				if (terms2.size() == 0) {
					return true;
				}
			}
		}
		return false;

	}

	public int getStartingPos() {
		return startingPos;
	}

	public int getEndingPos() {
		return endingPos;
	}

	public int getLength() {
		return length;
	}

	public int getPos(int index) {
		String currTerm = terms.get(index);
		for (Term t : termsContained) {
			if (t.index >= startingPos && t.index <= endingPos) {
				if (t.term.equals(currTerm)) {
					return t.index;
				}
			}
		}
		return 10086;
	}

}
