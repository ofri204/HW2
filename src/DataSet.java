public class DataSet {

    private String[] data;
    private int size;
    private int activeItems;
    private int addItemNotGenericError = -1;

    public DataSet( int size ){
        this.size = size;
        data = new String[ this.size ];
    }

    public boolean isEmpty(){
        return activeItems > 0;
    }

    public boolean isFull(){
        return activeItems == size;
    }

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

        for( int i=0; i< this.size-1; i++ ){
            newDataSet[i] = data[i];
        }

        data = newDataSet;
    }

    public int notGenericAddItem( String item ){

        if( isFull() ){
            return addItemNotGenericError;
        }

        this.data [ activeItems ] = new String( item );
        activeItems++;

        return 0;
    }


}
