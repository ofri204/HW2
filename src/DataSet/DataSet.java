package DataSet;

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
    public boolean isEmpty(){ return activeItems == 0; }

    /**
     * Checks if {@code data} is full
     * <p>Note: {@code data}  is full if all items inside aren't nulls</p>
     * @return 1 if {@code data} is full
     */
    public boolean isFull(){
        return activeItems == size;
    }

    /**
     Prints item from {@code data}
     @param index the index of item for displaying from {@code data}
     */
    public void printItemByIndex( int index ){
        System.out.print( this.data[index] );
    }

    /**
     * Adds items generically to {@code data}
     * <p>Note: The current function increase the length of {@code data}</p>
     * @param item new item for adding to {@code data}
     */
    public void genericAddItem( String item ){

        if( isFull() ){
            broadDataSet();
        }

        this.data [ activeItems ] = new String( item );
        activeItems++;
    }

    /**
     * Expanding {@code data} by 1 unit
     */
    private void broadDataSet(){

        this.size++;
        String[] newDataSet = new String[this.size];

        for( int i=0; i < this.size-1; i++ ){
            newDataSet[i] = data[i];
        }

        data = newDataSet;
    }

    /**
     * Adds items to {@code data}
     * <p>Note 1: the function isn't generic.</p>
     * <p>Note 2: if {@code data} is full, the function won't add the new item</p>
     *
     * @param item new item for adding to {@code data}
     * @return 0 if the item added, otherwise {@code ERROR}
     */
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
