package com.shimika.dfa;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


public class MainActivity extends ActionBarActivity {

    EditText editTextDFA, editTextNFA;
    Button buttonDFA, buttonNFA;

    // DFA
    int[] nextDFA, bit;

    // NFA
    NFAGraph graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextDFA = (EditText)findViewById(R.id.editTextDFA);
        editTextNFA = (EditText)findViewById(R.id.editTextNFA);
        buttonDFA = (Button)findViewById(R.id.buttonDFA);
        buttonNFA = (Button)findViewById(R.id.buttonNFA);

        /* DFA Init */

        nextDFA = new int[99];
        bit = new int[99];

        nextDFA[11] = 20;
        nextDFA[20] = 34;
        nextDFA[34] = 12;
        nextDFA[12] = 18;
        nextDFA[18] = 36;
        nextDFA[36] = 10;
        nextDFA[10] = 20;

        bit[1] = 1;
        for (int i = 2; i <= 6; i++) {
            bit[i] = bit[i - 1] * 2;
        }

        /* NFA Init */

        graph = new NFAGraph(6);
        graph.add(1, 2, -1);
        graph.add(1, 4, -1);
        graph.add(2, 3, 0);
        graph.add(3, 2, 0);
        graph.add(4, 5, 0);
        graph.add(5, 6, 0);
        graph.add(6, 4, 0);

        /* Button event handler */

        buttonDFA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editTextDFA.getText().toString();
                int location = 11;

                for (char c : input.toCharArray()) {
                    if (c == '0') {
                        location = nextDFA[location];
                    }
                }

                Boolean ok = false;
                if ((location & bit[2]) != 0 || (location & bit[4]) != 0) {
                    ok = true;
                }

                Toast.makeText(getApplicationContext(), String.format("DFA : %s", ok ? "YES" : "NO"), Toast.LENGTH_SHORT).show();
            }
        });

        buttonNFA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editTextNFA.getText().toString();

                int[] in = new int[99];
                int len = 0;

                for (char c : input.toCharArray()) {
                    if (c == '0') {
                        in[len++] = 0;
                    }
                }

                Boolean[][] check = new Boolean[7][len + 2];
                Edge getvalue;
                int node, iterator, nextNode;

                Queue<Edge> queue = new LinkedList<Edge>();
                queue.add(new Edge(1, 0));

                Log.d("NFA", Integer.toString(len));

                for(int i = 1; i <= 6; i++) {
                    for(int j = 0; j <= len + 1; j++) {
                        check[i][j] = false;
                    }
                }
                check[1][0] = true;

                try {
                    do {
                        getvalue = queue.remove();
                        node = getvalue.getNextNode();
                        iterator = getvalue.getValue();

                        if (iterator > len) {
                            continue;
                        }

                        ArrayList<Edge> list = graph.getList(node);
                        for (Edge edge : list) {
                            nextNode = edge.getNextNode();

                            if (edge.getValue() == -1 && check[nextNode][iterator] == false) {
                                check[nextNode][iterator] = true;
                                queue.add(new Edge(nextNode, iterator));
                            }

                            if (iterator < len && edge.getValue() == in[iterator] && check[nextNode][iterator + 1] == false) {
                                check[nextNode][iterator + 1] = true;
                                queue.add(new Edge(nextNode, iterator + 1));
                            }
                        }
                    } while (queue.size() > 0);

                    for(int i = 1; i <= 6; i++) {
                        String s = "";
                        for(int j = 0; j <= len; j++) {
                            if(check[i][j]) {
                                s+="1 ";
                            }else{
                                s+="0 ";
                            }
                        }
                        Log.d("NFA", s);
                    }

                    Log.d("NFA", "");

                    Boolean ok = false;
                    if (check[2][len] || check[4][len]) {
                        ok = true;
                    }

                    Toast.makeText(getApplicationContext(), String.format("NFA : %s", ok ? "YES" : "NO"), Toast.LENGTH_SHORT).show();

                } catch (Exception ex) {
                    Log.d("NFA", ex.getMessage());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_info) {
            Toast.makeText(getApplicationContext(), "2014210054 윤지훈", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
