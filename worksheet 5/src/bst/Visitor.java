package bst;

public interface Visitor<T> {
	public void visit(Position<T> position, Object data);
}
