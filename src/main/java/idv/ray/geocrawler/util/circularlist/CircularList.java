package idv.ray.geocrawler.util.circularlist;

import java.util.ArrayList;
import java.util.Set;

public class CircularList<E> extends ArrayList<E> {
	@Override
    public E get(int index) {
        return super.get(index % size());
    }
	
	public CircularList(Set<E> set) {
		super(set);
	}
	
	public CircularList() {
		super();
	}
}
