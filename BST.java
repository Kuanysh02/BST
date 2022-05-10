public V remove(K key){
        int index = getIndex(key);
        int hash = hash(key);
        HashNode<K, V> head = chainArray.get(index);
        HashNode<K, V> prev = null;
        if (head != null) {
        do {
        if (!head.key.equals ( key ) || hash != head.hashCode) {
        prev = head;
        head = head.next;
        } else {
        break;
        }
        } while (head != null);
        }
        if (head != null) {
        --size;

        if (prev == null) {
        chainArray.set ( index , head.next );
        } else {
        prev.next = head.next;
        }
        return head.val;
        } else {
        return null;
        }

        }

public boolean contains(V value){
        int i = 0;
        if (i < mSize) {
        do {
        if (chainArray.get ( i ) != null) {
        HashNode<K, V> head = chainArray.get ( i );
        if (head != null) {
        do {
        if (head.val.equals ( value )) {
        return true;
        }
        head = head.next;
        } while (head != null);
        }
        }
        ++i;
        } while (i < mSize);
        }
        return false;
        }

public K getKey(V val){
        return null;
        }

public int size(){
        return size;
        }

        }