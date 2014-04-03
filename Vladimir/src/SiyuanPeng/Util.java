package SiyuanPeng;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Util {
	Connection con=null;
	public List<InfoSchemaBean> list=null;
	
	public void groupBy(List<HashMap<String, String>> argList, String str){
		for (HashMap<String, String> map : argList ) {
			String s=map.get(list.get(2).getColumn_name());
			System.out.println(s);
		}
	}
	
	public List<HashMap<String, String>> mfTable(){
		String str="month";
		List<HashMap<String, String>> tuple=null;
		try {
			con=DBUtil.getInstance().getConnection();
			tuple=new ArrayList<HashMap<String, String>>();
			ResultSet rs;          			 //resultset object gets the set of values retreived from the database
			Statement st;
			st = con.createStatement();	  //statement created to execute the query
			String sql = "select * from sales;";
			rs = st.executeQuery(sql);              //executing the query 
			
			int numberOfColumn=0;
			for (int i=0; i<list.size(); i++) {
				if(list.get(i).getColumn_name().equals(str)){
					numberOfColumn=i;
					break;
				}
			}
			System.out.println(numberOfColumn);
			
			while(rs.next()){
				HashMap<String, String> temp=new HashMap<String, String>();
//				if()
				
				for (int i = 0; i < list.size(); i++) {
				temp.put(list.get(i).getColumn_name(), rs.getString(i+1));
					
				}
				
//				temp.put(list.get(1).getColumn_name(), rs.getString(2));
//				temp.put(list.get(2).getColumn_name(), rs.getString(3));
//				temp.put(list.get(3).getColumn_name(), rs.getString(4));
				tuple.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		for (HashMap<String, String> map : tuple ) {
			String s=map.get(list.get(2).getColumn_name());
			System.out.println(s);
		}
		return tuple;
	}

	/**
	 * Connect the database and retrieve the "information_schema" of the table.
	 * Put the result into an ArrayList to be easily used.
	 */
	public void mfStructureGenerator(){
		try {
			con=DBUtil.getInstance().getConnection();
			ResultSet rs;          			 //resultset object gets the set of values retreived from the database
			Statement st = con.createStatement();   //statement created to execute the query
			String sql = "SELECT table_name, column_name, is_nullable, data_type, character_maximum_length FROM INFORMATION_SCHEMA.Columns WHERE table_name = 'sales'";
			rs = st.executeQuery(sql);              //executing the query 
			list=new ArrayList<InfoSchemaBean>();
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

//			for (InfoSchemaBean bean : list) {
//				System.out.println(bean.getData_type());
//			}

		} catch(SQLException e) {
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}
	}
}
