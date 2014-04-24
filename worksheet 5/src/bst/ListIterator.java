package bst;
public class ListIterator<E> implements Iterator<E> {
	private List<E> list;
	private Position<E> current;

	public ListIterator(List<E> list) {
		this.list = list;
	}

	@Override
	public boolean hasNext() {
		if (list.isEmpty()) return false;
		if (current == null) return true;
		return !current.equals(list.last());
	}

	@Override
	public E next() {
		if (current == null) {
			current = list.first();
		} else {
			current = list.next(current);
		}
		return current.element();
	}
}
