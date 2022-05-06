public class PizzaCost{
    final String DB_URL;
    public static String getOrder (Statement stmt, String custNum )throws SQLException
    {
        //Declare a string
        String order;
        //Create a SELECT statement
        String sqlStatement; //Get the table

        //Send the SELECT statement to the DBMS
        ResultSet result = stmt.executeQuery(sqlStatement);

        //Display the contents of the result set
        if (result.next())
        {
            //Display and format the order information in strings
        }
        return order;
    }
    public static int getPrice{
        //go through the database and get prices for the order
    }

}
