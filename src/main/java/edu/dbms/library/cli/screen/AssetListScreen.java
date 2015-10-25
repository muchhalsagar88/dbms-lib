package edu.dbms.library.cli.screen;

import java.util.List;

import edu.dbms.library.cli.route.Route;
import edu.dbms.library.cli.route.RouteConstant;
import edu.dbms.library.db.DBUtils;

public abstract class AssetListScreen<T> extends BaseScreen {

	public AssetListScreen(){
		super();
		options.put(1, new Route(RouteConstant.PATRON_BASE));
		options.put(2, new Route(RouteConstant.BACK));
		options.put(3, new Route(RouteConstant.LOGOUT));
	}
	
	protected List<T> assets;

	protected List<T> getAssetList(Class<T> c) {
		
		String query = "SELECT e FROM "+c.getName()+" e"
				+" WHERE e.id NOT IN"
				+"(SELECT a.asset.id FROM AssetCheckout a WHERE a.dueDate IS NULL)";
		
		return (List<T>)DBUtils.fetchAllEntities(query);
	}
	
	//protected abstract Asset mapAssetToInputOption(int option);
	
	protected T mapAssetToInputOption(int option) {
		
		if(! (option > assets.size()+1))
			return assets.get(option-1);
		return null;
	}
	
	@Override
	public void displayOptions() {
		
		/*String[] title = {""};
		String[][] options = { 
							{Constant.OPTION_B},
							{Constant.OPTION_RESOURCES},
							{Constant.OPTION_CHKDOUT_RES},
							{Constant.OPTION_RES_REQUEST},
							{Constant.OPTION_NOTIFICATIONS},
							{Constant.OPTION_BALANCE},
							{Constant.OPTION_LOGOUT}
							};
		TextTable tt = new TextTable(title, options);
		tt.setAddRowNumbering(true);
		tt.printTable();*/
	}
}
