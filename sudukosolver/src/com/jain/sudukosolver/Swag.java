package com.jain.sudukosolver;


import java.util.ArrayList;
import java.util.Random;

import com.jain.sudukosolver.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class Swag extends Activity {
	private EditText arr[][];
	private LinearLayout layout;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swag);
		arr = new EditText[9][9];
		/*LinearLayout parent = (LinearLayout) findViewById(R.id.layout);
		int width = parent.getWidth();
		int height = parent.getHeight();
		int min = (width<=height)? width:height;
		layout = new LinearLayout(this);
		layout.setLayoutParams(new TableRow.LayoutParams(min, min));
		Log.e(null, min+"asdasd");
		layout.setOrientation(LinearLayout.VERTICAL);
		parent.addView(layout);*/
		layout = (LinearLayout) findViewById(R.id.layout);
		add();
	}
	public void add(){
		for(int i =0; i<9; i++){
			if(i%3==0){
				topBar();
			}
			topBar();
			LinearLayout temp = new LinearLayout(this);
			temp.setOrientation(LinearLayout.HORIZONTAL);
			temp.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, 0, 10.0f));
			TextView tmp = new TextView(this);
			tmp.setBackgroundColor(Color.BLACK);
			tmp.setLayoutParams(new TableRow.LayoutParams(0,LayoutParams.MATCH_PARENT,1.0f));
			temp.addView(tmp);
			for(int j =0; j<9; j++){
				tmp = new TextView(this);
				tmp.setBackgroundColor(Color.BLACK);
				tmp.setLayoutParams(new TableRow.LayoutParams(0,LayoutParams.MATCH_PARENT,1.0f));
				temp.addView(tmp);
				if(j%3==0){
					tmp = new TextView(this);
					tmp.setBackgroundColor(Color.BLACK);
					tmp.setLayoutParams(new TableRow.LayoutParams(0,LayoutParams.MATCH_PARENT,1.0f));
					temp.addView(tmp);
				}
				EditText txt = new EditText(this);
				txt.setGravity(Gravity.CENTER);
				txt.setBackgroundColor(Color.WHITE);
				txt.setLayoutParams(new TableRow.LayoutParams(0,LayoutParams.MATCH_PARENT,10.0f));
				txt.setTextSize(15);
				temp.addView(txt);
				txt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
				arr[i][j] = txt;
			}
			tmp = new TextView(this);
			tmp.setBackgroundColor(Color.BLACK);
			tmp.setLayoutParams(new TableRow.LayoutParams(0,LayoutParams.MATCH_PARENT,1.0f));
			temp.addView(tmp);
			tmp = new TextView(this);
			tmp.setBackgroundColor(Color.BLACK);
			tmp.setLayoutParams(new TableRow.LayoutParams(0,LayoutParams.MATCH_PARENT,1.0f));
			temp.addView(tmp);
			layout.addView(temp);
		}
		topBar();
		topBar();
	}
	public void topBar(){
		LinearLayout temp = new LinearLayout(this);
		TextView tmp = new TextView(this);
		tmp.setBackgroundColor(Color.BLACK);
		tmp.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
		temp.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1.0f));
		temp.addView(tmp);
		layout.addView(temp);
	}
	public void solve(View v){
		int suduko[][] = getNum();
		if(suduko==null){
			return;
		}
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				if(arr[i][j].getText().toString().equals("")){
					arr[i][j].setText("" + suduko[i][j]);
					arr[i][j].setBackgroundColor(Color.GREEN);
				}
			}
		}
	}
	public void hint(View v){
		int suduko[][] = getNum();
		ArrayList<int[]> str = new ArrayList<int[]>();
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				if(arr[i][j].getText().toString().equals("")){
					//arr[i][j].setText("" + suduko[i][j]);
					//arr[i][j].setBackgroundColor(Color.GREEN);
					int[] t = {i, j};
					str.add(t);
				}
			}
		}
		if(str.size()>0){
			Random rand = new Random();
			int index = rand.nextInt(str.size());
			int[] t = str.get(index);
			int i = t[0];
			int j = t[1];
			arr[i][j].setText("" + suduko[i][j]);
			arr[i][j].setBackgroundColor(Color.YELLOW);
		}
	}
	public void clear(View v){
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				arr[i][j].setText("");
				arr[i][j].setBackgroundColor(Color.WHITE);
			}
		}
	}
	public int[][] getNum(){
		int temp[][] = new int[9][9];
		try{
			for(int i = 0; i<9; i++){
				for(int j = 0; j<9; j++){
					if(!arr[i][j].getText().toString().equals("")){
						temp[i][j] = Integer.parseInt(arr[i][j].getText().toString());
					}else{
						temp[i][j] = 0;
					}
				}
			}
			Solver9 solver = new Solver9(temp);
			boolean solvable = solver.check();
			if(!solvable){
				arr[-1][-1].setText("asd");
			}
			solvable = solver.solve();
			if(!solvable){
				arr[-1][-1].setText("asd");
			}
			return solver.getArr();
		} catch(Exception e){
			inputError();
		}
		return null;
	}
	public void inputError(){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Please Input Valid Integers Between 0 and 9");
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		});
		alert.show();
	}
	public void exit(View v){
		finish();
	}
	@Override
	public void onBackPressed(){
		finish();
	}
}
