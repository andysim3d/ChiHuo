/**
 * Get the information_schema from the database and store it in a HashMap.
 * 
 */


package SiyuanPeng;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class UtilInfoSchema {
	Connection con=null;
	//string a means column name, string b means type name
	public HashMap<String, String> list=null;
	
	/**
	 * Connect the database and retrieve the "information_schema" of the table.
	 * Put the result into an ArrayList to be easily used.
	 */
	public void UtilGenerator(){
		try {
			con=DBUtil.getInstance().getConnection();
			ResultSet rs;  //resultset object gets the set of values retreived from the database
			Statement st = con.createStatement();   //statement created to execute the query
			String sql = "SELECT table_name, column_name, is_nullable, data_type, character_maximum_length FROM INFORMATION_SCHEMA.Columns WHERE table_name = 'sales'";
			rs = st.executeQuery(sql);    //executing the query 
			list=new HashMap<>();
			while(rs.next())
			{
				
				InfoSchemaBean temp=new InfoSchemaBean();
				temp.setTable_name(rs.getString(1));
				temp.setColumn_name(rs.getString(2));
				temp.setIs_nullable(rs.getString(3));
				temp.setData_type(rs.getString(4));
				temp.setCharacter_maximum_length(rs.getString(5));
				
				list.put(temp.getColumn_name(),temp.getData_type());
			}

		} catch(SQLException e) {
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}
	}
}
