package TYPES;

public class TYPE_CLASS_VAR_DEC_LIST
{
	public TYPE_CLASS_VAR_DEC head;
	public TYPE_CLASS_VAR_DEC_LIST tail;
	public TYPE_CLASS_VAR_DEC_LIST last;
	public TYPE_CLASS_VAR_DEC_LIST(TYPE_CLASS_VAR_DEC head,TYPE_CLASS_VAR_DEC_LIST tail)
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

	public void insert(TYPE_CLASS_VAR_DEC node){
		if (head == null){
			head = node;
			tail = null;
			last = this;
			return;
		}
		
		TYPE_CLASS_VAR_DEC_LIST newTail = new TYPE_CLASS_VAR_DEC_LIST(node, null);
		last.tail = newTail;
		last = newTail; 
	}
}
