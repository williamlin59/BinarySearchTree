package bst;



import java.util.Comparator;


public class BSTMap<K, V> implements Map<K,V> {
	private Comparator<K> comparator = new Comparator<K>() {
		@SuppressWarnings("unchecked")
		@Override
		public int compare(K arg0, K arg1) {
			if (arg0 instanceof Comparable && arg1 instanceof Comparable) {
				return ((Comparable<K>) arg0).compareTo(arg1);
			}
			
			throw new IncomparableException();
		}
	};
	
	private class BSTEntry implements Entry<K,V> {
		K key;
		V value;
		
		public BSTEntry(K k, V v) {
			key = k;
			value = v;
		}
		
		@Override
		public K key() {
			return key;
		}
		
		@Override
		public V value() {
			return value;
		}
		
		public String toString() {
			return "{" + key + "," + value + "}";
		}
	}
	
	private ProperBinaryTree<Entry<K,V>> tree;
	private int size;
	
	public BSTMap() {
		tree = new ProperBinaryTree<Entry<K,V>>();
		size = 0;
	}
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	private Position<Entry<K,V>> find(Position<Entry<K,V>> position, K key) {
		if (tree.isExternal(position)) return position;
		
		int result = comparator.compare(key, position.element().key());
		
		if (result < 0) return find(tree.left(position), key);
		else if (result > 0) return find(tree.right(position), key);
		
		return position;
	}

	@Override
	public V get(K k) {
		Position<Entry<K,V>> p = find(tree.root(), k);
		if(tree.isExternal(p)){
			return null;
		}
		return p.element().value();
		//return  position.;
	}

	@Override
	public V put(K k, V v) {
		Position<Entry<K,V>> position = find(tree.root(), k);
		BSTEntry temp=new BSTEntry(k, v);
		//Entry<K,V> temp=new Entry<k,v>;
		//Object back = null;
		if (tree.isInternal(position)) {
			// value is not in the tree so insert it here
			//tree.
			tree.replace(position,temp);
		}
		else{
			tree.expandExternal(position, temp);
		}
		//Position<T> position = find(tree.root(), value);
		//return (V)back;
		return v;
	}

	@Override
	public V remove(K k) {
		Position<Entry<K,V>> position = find(tree.root(),k);
		if (tree.isInternal(position)) {
			// value is in the tree so remove it
			if (tree.isInternal(tree.left(position)) && tree.isInternal(tree.right(position))) {
				// both children are internal, so find the position of the next largest value
				// copy the value and remove the position
				Position<Entry<K,V>> current = tree.right(position);
				while (tree.isInternal(tree.left(current))) current = tree.left(current);
				tree.replace(position, current.element());
				return tree.remove(current).value();
			} else {
				return tree.remove(position).value();
			}
		}
		return null;
	}

	@Override
	public Iterator<K> keys() {
		return new Iterator<K>() {
			Iterator<Entry<K,V>> iterator = entries();

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public K next() {
				return iterator.next().key();
			}
		};
	}

	@Override
	public Iterator<V> values() {
		return new Iterator<V>() {
			Iterator<Entry<K,V>> iterator = entries();

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public V next() {
				return iterator.next().value();
			}
		};
	}

	@Override
	public Iterator<Entry<K, V>> entries() {
		return tree.iterator();
	}

	public String toString() {
		return tree.toString();
	}
	public static void main(String[] args){
		BSTMap<String,String> test=new BSTMap<String,String>();
		 test.put("ie", "Ireland"); 
		 System.out.println(test.toString());
		 test.put("uk", "United Kingdom"); 
		 System.out.println(test.toString());
		 test.put("us", "USA"); 
		 System.out.println(test.toString());
		 test.put("de", "Germany"); 
		 System.out.println(test.toString());
		 test. put("fr", "France"); 
		 System.out.println(test.toString());
		 test. remove("uk"); 
		 System.out.println(test.toString());
		 test.put("us", "United States of America"); 
		 System.out.println(test.toString());
		 test.put("es", "Spain"); 
		 test. put("uk", "United Kingdom"); 
		 test. remove("ie"); 
		 System.out.println(test.toString());
		
	}
}
