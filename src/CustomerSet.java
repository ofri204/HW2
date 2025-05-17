public class CustomerSet {

    /**<p>Different System Errors</p>*/
    private static final int customerIsFullError = -1;
    private static final int customerIsExistingError = -2;
    private static final int customerIsNotExistingError = -3;
    private static final int functionCompletedSuccessfully = 0;

    /** array of customers */
    private Customer[] customers;
    /** length of {@code customers} */
    private int size;
    /** <p>number of customers in {@code customers} which aren't nulls</p>
     * <b>Note: {@code activeCustomers}-1 is the last position of a customer in {@code customers} which
     * isn't null, if {@code activeCustomers} > 0. </b>*/
    private int activeCustomers;

    /**Specifies if {@code customers} will not be expanded*/
    private boolean isFinalSize;

    /**<p>Main builder of MoviesSet Class</p>
     * @param size length of {@code customers}
     * @param isFinalSize defines if the length of {@code customers} is final*/
    public CustomerSet(int size, boolean isFinalSize){
        this.size = size;
        this.activeCustomers = 0;
        this.customers = new Customer[this.size];
        this.isFinalSize = isFinalSize;
    }

    /**
     * <p>Class getters</p>
     */
    public static int getCustomerIsFullError(){ return customerIsFullError; }
    public static int getCustomerIsExistingError(){ return customerIsExistingError; }
    public static int getCustomerIsNotExistingError(){ return customerIsNotExistingError; }
    public static int getFunctionCompletedSuccessfully(){ return functionCompletedSuccessfully; }

    /**
     * <p><u>Purpose: Check is {@code customers} is empty</u></p>
     * <p><b>Note: {@code customers} is empty if it has nulls in all indexes</b></p>
     * @return 1 if {@code customers} is empty, otherwise 0
     */
    public boolean isEmpty(){
        return this.activeCustomers == 0;
    }

    /**
     * <p><u>Purpose: Check is {@code customers} is full</u></p>
     * <p><b>Note: {@code customers} is full if it hasn't any nulls</b></p>
     * p><b>This is sub-function of {@link #addNewCustomer(Customer)} (customer)} </b></p>
     * @return 1 if {@code customers} is full, otherwise 0
     */
    public boolean isFull(){
        return this.activeCustomers == size;
    }

    /**
     * <p><u>Purpose: Checks if a customer is in {@code customers}</u></p>
     * p><b>This is sub-function of {@link #addNewCustomer(Customer)} (customer)} </b></p>
     * @param customer a customer
     * @return true if the customer is in {@code customers}, otherwise false
     * */
    public boolean isCustomerExisting( Customer customer ){
        return this.findCustomerByDetails( customer ) != CustomerSet.customerIsNotExistingError;
    }

    /**
     *<p><u>Purpose: Add new customer to {@code customers}</u></p>
     * <p><b>Note 1: if the new customer is existing in {@code customers} or there is no space for
     * it in {@code customers}, the new customer won't be added</b></p>
     * <p><b>Note 2: if {@code isFinalSize} is false, {@code customers} will be expanded</b></p>
     * <p><b>Used functions: {@link #isFull()}, {@link #isCustomerExisting(Customer)},
     * {@link #customerAdder(Customer)}</b></p>
     *
     * @param customer a new customer
     * @return <p>{@code customerIsFullError} if {@code customers} is full of customers</p>
     *          <p>{@code customerIsExistingError} if the customer is existing in {@code customers}</p>
     *          <p>otherwise, it returns {@code functionCompletedSuccessfully}</p>*/
    public int addNewCustomer(Customer customer){
        if (  this.isFinalSize && isFull() ){
            return customerIsFullError;
        } else if( isCustomerExisting( customer )){
            return customerIsExistingError;
        } else{
            customerAdder( customer );
            return functionCompletedSuccessfully;
        }
    }

    /**
     * <p><u>Purpose: Adds a customer to {@code customers} directly</u></p>
     * <p><b>This is sub-function of {@link #addNewCustomer(Customer)}</b></p>
     * <p><b>Used functions: {@link #expandCustomersArr()}</b></p>
     * @param customer a customer to add
     * */
    private void customerAdder(Customer customer){
        if( this.activeCustomers == this.size){
            expandCustomersArr();
        }
        this.customers[activeCustomers] = customer;
        this.activeCustomers++;
    }

    /**
     * <p><u>Purpose: expands {@code customers} by 1 unit</u></p>
     * <p><b>This is sub-function of {@link #customerAdder(Customer)}</b></p>
     * */
    private void expandCustomersArr(){
        this.size++;
        Customer[] newArr = new Customer[size];
        for(int i = 0; i < size - 1; i++){
            newArr[i] = this.customers[i];
        }
        this.customers = newArr;
    }

    /**
     * Finds and returns a customer in the system that matches the given customer
     *
     * @param customer the Customer object containing the details to search for
     * @return the matching Customer object if found; null if not found or index is invalid
     */
    public Customer findCustomer( Customer customer){
        return this.customers[ this.findCustomerByDetails( customer ) ];
    }


    /**
     * <p><u>Purpose: Finds customer in {@code customers}</u></p>
     * <p><b>Note: customer can be detected by 3 parameters: name, director and release year</b></p>
     * @param customer customer to be found
     * @return <p>index of the searched customer in {@code customers} if it is existing in
     * {@code customers},</p> <p>otherwise -1</p>
     * */
    public int findCustomerByDetails( Customer customer){
        int found = customerIsNotExistingError;
        for( int i = 0; i < this.activeCustomers; i++){
            if( this.customers[i].isEquals( customer) ){
                found = i;
                break;
            }
        }
        return found;
    }


    /**
     * <p><u>Purpose: Check if a customer can be removed from {@code customers} and remove it if so</u></p>
     * <p><b>Note: if the customer isn't in {@code customers}, it won't be removed from {@code customers}
     * </b></p>
     * <p><b>Used functions: {@link #customerRemover(Customer)}</b></p>
     * @param customer a customer to remove
     * @return {@code customerIsNotExistingError} if the customer isn't existing in {@code customers},
     * otherwise {@code functionCompletedSuccessfully}*/
    public int removeCustomer( Customer customer){
        if( !isCustomerExisting(customer) ){
            return customerIsNotExistingError;
        }

        customerRemover( customer );
        return functionCompletedSuccessfully;
    }


    /**<p><u>Purpose: Remove a customer from {@code customers} directly</u></p>
     * <p><b>This is sub-function of {@link #removeCustomer(Customer)}</b></p>
     * <p><b>Used functions: {@link #findCustomerByDetails(Customer)}</b></p>
     * @param customer a customer to remove*/
    private void customerRemover( Customer customer ){
        int removeIndex = findCustomerByDetails( customer );
        this.customers[ removeIndex ] = null;
        this.activeCustomers--;
        for( int i = removeIndex; i < this.activeCustomers; i++){
            this.customers[i] = this.customers[i+1];
        }
        this.customers[ this.activeCustomers + 1 ] = null;
    }

    /**
     * Searches for a customer in the system by their ID.
     *
     * @param Id the ID of the customer to find
     * @return the Customer object if found; null if no customer with the given ID exists
     */
    public Customer findCustomerById( String Id){
        for (int i=0; i<activeCustomers; i++){
            if( customers[i].getId().equals( Id ) ){
                return customers[i];
            }
        }
        return null;
    }



}



