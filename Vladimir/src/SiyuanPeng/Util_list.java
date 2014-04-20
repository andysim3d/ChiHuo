package SiyuanPeng;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Util_list {
	Connection con=null;
	public List<InfoSchemaBean> list=new ArrayList<>();;

	/**
	 * Connect the database and retrieve the "information_schema" of the table.
	 * Put the result into an ArrayList to be easily used.
	 */
	public void mfStructureGenerator(){
		String sql = "SELECT table_name, column_name, is_nullable, data_type, character_maximum_length FROM INFORMATION_SCHEMA.Columns WHERE table_name = 'sales'";
		try(Connection con=DBUtil.getInstance().getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);) {
			while(rs.next())
			{
				InfoSchemaBean temp=new InfoSchemaBean();
				temp.setTable_name(rs.getString(1));
				temp.setColumn_name(rs.getString(2));
				temp.setIs_nullable(rs.getString(3));
				temp.setData_type(rs.getString(4));
				temp.setCharacter_maximum_length(rs.getString(5));
				list.add(temp);
			}

		} catch(SQLException e) {
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}
	}
}
