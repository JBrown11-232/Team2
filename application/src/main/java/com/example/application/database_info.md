### PizzaDB
* Uses derby drivers, no username or password
* Contains 4 tables: Customer, AvailableOption, Pizza, UsedOption
* Customer and AvailableOption do not reference any tables
* Pizza references Customer table
* UsedOption references Pizza and Option tables
* The UsedOption table has no primary key
* There should be exactly 1 crust and 1 sauce per pizza
* A Pizza should not be allowed duplicate UsedOptions
* The price of a pizza is stored in the database (without tax) and should equal the sum of its options + price of its size
* Size of a pizza and type of option are stored as CHAR(1)
* Customer name, address, phone-number, and option name are VARCHAR (varying limits)
* All IDs are stored as INT

### PizzaDBManager
* All PizzaDBManager methods are static (so a PizzaDBManager object should never be instantiated)
* PizzaDBManager stores a "worldNum" which increments everytime the class modifies the database
* A race condition will occur if multiple methods in PizzaDBManager are called at the same time and at least one modifies the database
* Currently, the IDs given to entities are between 1-99999999 (inclusive)
* Currently, PID%3==0, OID%3==1, CID%3==2 so they never intersect
* Currently, no input checking is done on any parameters
* Remove methods on Pizzas, Options, or Customers remove entries that reference them
* Currently, insert methods do not check for pre-existing entries
* Currently, update methods do not check for non-existent entries
* Update methods cannot change primary keys
* Most retrieval methods don't error when no results can be found
  * If the method returns an object of Pizza, Option, or Customer, it returns null
  * If the method returns an ArrayList, it returns an empty ArrayList
  * If the method returns a String, it returns empty string ""
  * If the method returns an int, it returns -1
  * If the method returns a double, it returns -1.0

### PizzaDBEntity Objects
* Methods of PizzaDBEntity objects never access the database!
  * Creating a new PizzaDBEntity does NOT create an entry in the database
* PizzaDBEntity objects store the world number they were created at
* PizzaDBEntity objects .equals() is true if the 2 objects have identical classes, world numbers, and IDs
  * If the classes don't match, they are not equal
  * Else if the world numbers don't match, then they are equal iff the contained data (excluding world num) are all equal
* All PizzaDBEntity objects should be treated as immutable
* ArrayLists passed to Pizza constructors or returned from Pizza methods should be shallow-copied
* Here is how the objects represent the database tables
  * Customer table                          -> Customer objects
  * AvailableOption table                   -> Option objects
  * UsedOption table                        -> ArrayList<Option> objects
  * Pizza table + ArrayList<Option> objects -> Pizza objects
* Pizza objects contain a Customer object and multiple Option objects
