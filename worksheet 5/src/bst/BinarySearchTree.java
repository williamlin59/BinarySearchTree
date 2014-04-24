package bst;

import java.util.Comparator;

public class BinarySearchTree<T> {
	/**
	 * This anonymous class implements a "default" comparator that 
	 * compares two values arg0, arg1 if they both implement the 
	 * comparable interface.
	 * 
	 * If not an IncomparableException is thrown.
	 * 
	 * The Comparable interface is a standard interface provided
	 * by Java and is implemented by all the classes whose instances
	 * you would expect to be comparable (i.e. String, Integer,
	 * Long, ...)
	 */
	private Comparator<T> comparator = new Comparator<T>() {
		@SuppressWarnings("unchecked")
		@Override
		public int compare(T arg0, T arg1) {
			if (arg0 instanceof Comparable && arg1 instanceof Comparable) {
				return ((Comparable<T>) arg0).compareTo(arg1);
			}
			
			throw new IncomparableException();
		}
	};
	
	private ProperBinaryTree<T> tree = new ProperBinaryTree<T>();
	
	private Position<T> find(Position<T> position, T value) {
		if (tree.isExternal(position)) return position;
		
		int result = comparator.compare(value, position.element());
		
		if (result < 0) return find(tree.left(position), value);
		else if (result > 0) return find(tree.right(position), value);
		
		return position;
	}

	public void insert(T value) {
		Position<T> position = find(tree.root(), value);
		if (tree.isExternal(position)) {
			// value is not in the tree so insert it here
			tree.expandExternal(position, value);
		}
	}

	public void remove(T value) {
		Position<T> position = find(tree.root(), value);
		if (tree.isInternal(position)) {
			// value is in the tree so remove it
			if (tree.isInternal(tree.left(position)) && tree.isInternal(tree.right(position))) {
				// both children are internal, so find the position of the next largest value
				// copy the value and remove the position
				Position<T> current = tree.right(position);
				while (tree.isInternal(tree.left(current))) current = tree.left(current);
				tree.replace(position, current.element());
				tree.remove(current);
			} else {
				tree.remove(position);
			}
		}
	}
	
	public boolean contains(T value) {
		Position<T> position = find(tree.root(), value);
		return tree.isInternal(position);
	}
	
	public String toString() {
		return tree.toString();
	}
	
	public static void main(String[] args) {
		BinarySearchTree<String> tree = new BinarySearchTree<String>();
		System.out.println(tree);
		tree.insert("Rem");
		System.out.println(tree);
		tree.insert("Arthur");
		System.out.println(tree);
		tree.insert("Fred");
		System.out.println(tree);
		tree.insert("Trevor");
		System.out.println(tree);
	}
}
