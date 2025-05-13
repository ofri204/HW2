public class DataSet {

    /** array of information*/
    private String[] data;
    /** length of data */
    private int size;
    /** number of items in data which aren't nulls */
    private int activeItems;
    /** errors value */
    private final int ERROR = -1;

    /**
     * Class Builder
     * @param size length of {@code data}
     */
    public DataSet( int size ){
        this.size = size;
        data = new String[ this.size ];
    }

    /**
     * Checks if {@code data} is empty
     * @return 1 if {@code data} is empty, otherwise 0
     */
    public boolean isEmpty(){
        return activeItems == 0;
    }

    /**
     * Checks if {@code data} is full
     * <p>Note: {@code data}  is full if all items inside aren't nulls</p>
     * @return 1 if {@code data} is full
     */
    public boolean isFull(){
        return activeItems == size;
    }

    /**

     */
    public void printItemByIndex( int index ){
        System.out.print( this.data[index] );
    }

    public void genericAddItem( String item ){

        if( isFull() ){
            broadDataSet();
        }

        this.data [ activeItems ] = new String( item );
        activeItems++;
    }

    private void broadDataSet(){

        this.size++;
        String[] newDataSet = new String[this.size];

        for( int i=0; i < this.size-1; i++ ){
            newDataSet[i] = data[i];
        }

        data = newDataSet;
    }

    public int notGenericAddItem( String item ){

        if( isFull() ){
            return ERROR;
        }

        this.data [ activeItems ] = new String( item );
        activeItems++;

        return 0;
    }

    public int removeItemByData( String item ){

        int itemIndex = findItemByName( item );

        if( itemIndex == ERROR){
            return ERROR;
        }

        data[itemIndex] = null;
        moveSetBackward( itemIndex );

        return 0;
    }

    private int findItemByName( String item ){

        for( int i = 0; i < this.size; i++){
            if( data[i].equals(item) ){
                return i;
            }
        }
        return ERROR;
    }

    private void moveSetBackward( int startMovePosition ){
        int i = startMovePosition;
        while( i+1 < this.size ){
            data[ i+1 ] = data[i];
            i++;
        }
    }
}
