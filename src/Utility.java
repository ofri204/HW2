public final class Utility {

    /**Properties of Utility*/
    private final Object[] arr;
    private final int size;
    private int activeObjectNum = 0;
    private final Class arrObjClass;

    /**Errors*/
    public static final int ITEM_NOT_FOUND_ERROR = -1;

    /**Main Utility Constructor*/
    public Utility( int length, Class objClass){
        this.arr = new Object[  length ];
        this.size = length;
        this.arrObjClass = objClass;
    }

    /**
     * <p><u>Checks if 2 meta-objects are identical</u></p>
     * @param class1 meta-object 1
     * @param class2 meta-object 2
     * @return true if they are identical, false otherwise
     * */
    public static boolean isClassesIdentical( Class class1, Class class2){
        return class1.equals( class2 ) ;
    }

    /**<p><u>Checks if current array is empty</u></p>
     * @return true if is empty, false otherwise
     * */
    public boolean isArrEmpty(){
        return this.activeObjectNum == 0;
    }

    /**<p><u>Checks if an object is null</u></p>
     * @param obj an object
     * @return true if it is not null, false otherwise*/
    private static boolean isObjNotNull( Object obj ){
        return obj != null;
    }

    /**<p><u>Check if the object has the same class as the other objects in the array</u></p>
     * @param obj an object
     * @return true if the object has the same class as other objects in the array*/
    private boolean canObjBeInArr( Object obj ){
        return this.arrObjClass.equals(obj.getClass());
    }

    /**<p><u>Checks if the array is full</u></p>
     * @return true if it is not full, false otherwise*/
    public boolean isArrNotFull(){
        return this.size > this.activeObjectNum;
    }

    /**<p><u>Finds an item and returns its reference</u></p>
     * @param obj an object
     * @return the object reference from the array, null otherwise*/
    public Object findItemAndReturnReference( Object obj ){
        int index = this.findItem( obj );
        if( index == Utility.ITEM_NOT_FOUND_ERROR){
            return null;
        }
        return this.arr[index];
    }

    /**<p><u>Returns active objects from array</u></p>
     * @param index index in the array
     * @return the object from the array if the index has active object, otherwise false*/
    public Object returnItemByIndex(int index){
        if( index >= this.activeObjectNum ){
            return null;
        }
        return this.arr[index];
    }

    /**<p><u>Finds the index of an object in the array if it exists</u></p>
     * <br><b>Note: if the object doesn't exist it return {@code ITEM_NOT_FOUND_ERROR}</b>
     * @param obj an object
     * @return index of the object in the array if it exists,
     * {@code ITEM_NOT_FOUND_ERROR} otherwise*/
    public int findItem( Object obj ){
        for( int i = 0; i < this.activeObjectNum; i++){
            if( Utility.isEquals( obj, this.arr[i] ) ){
                return i;
            }
        }
        return Utility.ITEM_NOT_FOUND_ERROR;
    }

    /**<p><u>Sub-function of {@link #isItemExist(Object)}</u></p>
     * @param obj an object
     * @return true if it exists in array, false otherwise*/
    private boolean isItemExistSub( Object obj ){
        return this.findItem( obj ) != Utility.ITEM_NOT_FOUND_ERROR;
    }

    /**<p><u>Checks if 2 objects are identical</u></p>
     * @param obj1 an object
     * @param obj2 another object
     * @return true if they are identical, false otherwise*/
    public static boolean isEquals( Object obj1, Object obj2 ){
        return obj1.equals(obj2);
    }

    /**<p><u>Checks if an object exists in array</u></p>
     * @param obj an object
     * @return true if it exists in the array, false otherwise*/
    public boolean isItemExist(Object obj){
        return ( Utility.isObjNotNull(obj) && this.canObjBeInArr(obj) && this.isItemExistSub(obj) );
    }

    /**<p><u>Deletes an object from array if it is existing</u></p>
     * @param obj an object*/
    public void removeItem( Object obj ){
        if( !Utility.isObjNotNull(obj) || !this.isItemExist(obj)){ //checks that the object exist
            return;
        }
        //removing the object and putting the last existing object to the last deleted position
        int index = this.findItem( obj );
        this.arr[index] = null;
        this.arr[index] = arr[this.activeObjectNum - 1];
        this.arr[this.activeObjectNum - 1 ] = null;
        this.activeObjectNum--;

    }


    /**<p><u>Add item to array</u></p>
     * @param obj an object*/
    public void addItem(Object obj){
        //checks that the array isn't full, null, and not exist in the array
        if( this.isArrNotFull( ) && Utility.isObjNotNull( obj ) && !this.isItemExist(obj) &&
                this.isArrNotFull() ){
                this.arr[this.activeObjectNum] = obj;
                this.activeObjectNum++;
        }
    }
}
