package TYPES;

public class TYPE_LIST extends TYPE
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public TYPE head;
	public TYPE_LIST tail;
	public TYPE_LIST last;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public TYPE_LIST(TYPE head,TYPE_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public int getLength()
	{
		if (head == null) return 0;
		if (tail == null) return 1;
		return 1 + tail.getLength();
	}

	public void insert(TYPE node){
		if (head == null){
			head = node;
			tail = null;
			last = this;
			return;
		}
		
		TYPE_LIST newTail = new TYPE_LIST(node, null);
		last.tail = newTail;
		last = newTail; 
	}

	public TYPE_LIST copy(){
		TYPE_LIST currHead = this;
		TYPE_LIST copied = new TYPE_LIST(null, null);
		while (currHead != null && currHead.head != null){
			copied.insert(currHead.head.copy());
			currHead = currHead.tail;
		}
		return copied;
	}
}
