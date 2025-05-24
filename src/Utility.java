public final class Utility {
    private final Object[] arr;
    private final int size;
    private int activeObjectNum = 0;
    private final Class arrObjClass;

    public Utility( int length, Class objClass){
        this.arr = new Object[  length ];
        this.size = length;
        this.arrObjClass = objClass;
    }

    public static boolean isClassesIdentical( Class class1, Class class2){
        return class1.equals( class2 ) ;
    }


    public boolean isArrEmpty(){
        return this.activeObjectNum == 0;
    }


    private static boolean isObjNotNull( Object obj ){
        return obj != null;
    }
    private static boolean isInstanceOfSpecialEquality( Object obj ){
        return obj instanceof IsEquableSpecially;
    }

    private boolean canObjBeInArr( Object obj ){
        return this.arrObjClass.equals(obj.getClass());
    }

    public boolean isArrNotFull(){
        return this.size > this.activeObjectNum;
    }

    public static final int ITEM_NOT_FOUND_ERROR = -1;

    public Object findItemAndReturnReference( Object obj){
        int index = this.findItem( obj );
        if( index == Utility.ITEM_NOT_FOUND_ERROR){
            return null;
        }
        return this.arr[index];
    }

    public Object returnItemByIndex(int index){
        if( index >= this.activeObjectNum ){
            return null;
        }
        return this.arr[index];
    }

    public int findItem( Object obj ){
        for( int i = 0; i < this.activeObjectNum; i++){
            if( Utility.isEquals( obj, this.arr[i] ) ){
                return i;
            }
        }
        return Utility.ITEM_NOT_FOUND_ERROR;
    }

    private boolean isItemExistSub( Object obj ){
        return this.findItem( obj ) != Utility.ITEM_NOT_FOUND_ERROR;
    }

    public static boolean isEquals( Object obj1, Object obj2 ){
        return ((IsEquableSpecially)obj1).isEquals(obj2);
    }

    public boolean isItemExist(Object obj){
        return ( Utility.isObjNotNull(obj) && this.canObjBeInArr(obj) && this.isItemExistSub(obj) );
    }

    public void removeItem( Object obj ){
        if( !Utility.isObjNotNull(obj) || !this.isItemExist(obj)){
            return;
        }
        int index = this.findItem( obj );
        this.arr[index] = null;
        this.arr[index] = arr[this.activeObjectNum - 1];
        this.arr[this.activeObjectNum - 1 ] = null;
        this.activeObjectNum--;

    }


    public void addItem(Object obj){
        if( this.isArrNotFull( ) && Utility.isObjNotNull( obj ) && !this.isItemExist(obj) &&
                this.isArrNotFull() ){
                this.arr[this.activeObjectNum] = obj;
                this.activeObjectNum++;
        }
    }
}

interface IsEquableSpecially{
    boolean isEquals(Object obj);
    boolean isObjKindOf(Object obj);
}