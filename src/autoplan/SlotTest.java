public class SlotTest{
	public static void print( String s ){
		System.out.println(s);
	}
	public static void main( String[] args ){
		try{	
		String startTest = "2012-07-10 14:58";
		String endTest = "2012-07-10 17:01";
		
		Slot mySlot = new Event( startTest, endTest );

		print( mySlot.getStartDate().toString() );
		print( mySlot.getEndDate().toString() );
		print( mySlot.getDuration() + "" );
		} catch( Exception e ){}
	}
}
