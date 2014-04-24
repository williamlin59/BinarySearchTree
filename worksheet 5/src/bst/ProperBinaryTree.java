package bst;

public class ProperBinaryTree<T> extends AbstractBinaryTree<T> {
	public ProperBinaryTree() {
		root = new Node(null);
		size = 1;
	}
	
	public void expandExternal(Position<T> p, T element) {
		if (isInternal(p)) throw new InvalidPositionException();
		
		Node node = toNode(p);
		node.element = element;
		node.left = new Node(null);
		node.left.parent = node;
		node.right= new Node(null);
		node.right.parent = node;
		size += 2;
	}
	
	public T remove(Position<T> p) {
		if (isExternal(p)) throw new InvalidPositionException();
		
		if (isExternal(left(p))) {
			// left child is external, so can construct new tree
			// by deleting left child of p and replacing p with
			// its right child
			Node node = toNode(p);
			if (isRoot(p)) {
				// p is the root node, so make the right child
				// the root node
				root = node.right;
			} else if (left(parent(p)) == p) {
				// p is the left child of its parent, so make
				// the right child the left child of p's parent
				node.parent.left = node.right;
			} else {
				// p must be the right child of its parent, so make
				// the right child the right child of p's parent
				node.parent.right = node.right;
			}
			
			// make the right childs parent the same as p's parent
			// when p is the root, this will be null, which is
			// correct
			node.right.parent = node.parent;
			
			// remove links between left child of p and p
			node.left.parent = null;
			node.left = null;
		} else if (isExternal(right(p))) {
			// right child is external, so can construct new tree
			// by deleting right child of p and replacing p with
			// its left child

			Node node = toNode(p);
			if (isRoot(p)) {
				root = node.left;
			} else if (left(parent(p)) == p) {
				node.parent.left = node.left;
			} else {
				node.parent.right = node.left;
			}
			
			node.left.parent = node.parent;
			node.right.parent = null;
			node.right = null;
		} else {
			// two internal children, so need to throw an exception
			throw new InvalidPositionException();
		}
		return p.element();
	}

	/**
	 * Modified version of inorder method in AbstractBinaryTree which only visits left or
	 * right child if they are internal.
	 * 
	 * @param position
	 * @param list
	 */
	protected void inorder(Position<T> position, List<Position<T>> list) {
		if (hasLeft(position) && isInternal(left(position))) inorder(left(position), list);
		list.insertLast(position);
		if (hasRight(position) && isInternal(right(position))) inorder(right(position), list);
	}
}
