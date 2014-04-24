package bst;

public abstract class AbstractBinaryTree<T> implements BinaryTree<T> {
	protected class Node implements Position<T> {
		T element;
		Node parent, left, right;
		
		public Node(T element) {
			this.element = element;
		}
		
		@Override
		public T element() {
			return element;
		}
	}
	
	protected Node root;
	protected int size = 0;
	
	@Override
	public Position<T> root() {
		return root;
	}

	protected Node toNode(Position<T> p) {
		return (Node) p;
	}
	
	@Override
	public Position<T> parent(Position<T> p) {
		return toNode(p).parent;
	}

	@Override
	public Iterator<Position<T>> children(final Position<T> p) {
		return new Iterator<Position<T>>() {
			Node current = toNode(p).left;
			
			@Override
			public boolean hasNext() {
				return (current != null);			}

			@Override
			public Position<T> next() {
				Node n = current;
				if (current == toNode(p).left) {
					current = toNode(p).right;
				} else {
					current = null;
				}
				return n;
			}
			
		};
	}

	@Override
	public boolean isInternal(Position<T> p) {
		return toNode(p).left != null || toNode(p).right != null;
	}

	@Override
	public boolean isExternal(Position<T> p) {
		return toNode(p).left == null && toNode(p).right == null;
	}

	@Override
	public boolean isRoot(Position<T> p) {
		return p == root;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	protected void inorder(Position<T> position, List<Position<T>> list) {
		if (hasLeft(position)) inorder(left(position), list);
		list.insertLast(position);
		if (hasRight(position)) inorder(right(position), list);
	}
	
	@Override
	public Iterator<Position<T>> positions() {
		List<Position<T>> list = new LinkedList<Position<T>>();
		
		// do an in-order traversal of the tree and build a list
		// of the nodes in the tree.
		inorder(root, list);
		
		return list.iterator();
	}

	/**
	 * Create a custom iterator that used the positions() iterator
	 */
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			Iterator<Position<T>> iterator = positions();

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public T next() {
				return iterator.next().element();
			}
		};
	}
	
	@Override
	public T replace(Position<T> p, T t) {
		T temp = p.element();
		toNode(p).element = t;
		return temp;
	}

	@Override
	public Position<T> left(Position<T> p) {
		return toNode(p).left;
	}

	@Override
	public Position<T> right(Position<T> p) {
		return toNode(p).right;
	}

	@Override
	public boolean hasLeft(Position<T> p) {
		return toNode(p).left != null;
	}

	@Override
	public boolean hasRight(Position<T> p) {
		return toNode(p).right != null;
	}

	/**
	 * This is not a standard tree traversal operation
	 * it is designed so that the output is:
	 *     right child
	 * node
	 *         right child of left child
	 *     left child
	 *         left child of left child
	 */
	private String toString(Position<T> p, String tabs) {
		String out = "";
		if (hasRight(p)) out += toString(right(p), tabs+"\t");
		out += tabs + p.element() + "\n";
		if (hasLeft(p)) out += toString(left(p), tabs+"\t");
		return out;
	}
	
	public String toString() {
		return toString(root, "");
	}
}
