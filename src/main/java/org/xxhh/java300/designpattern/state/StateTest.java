package org.xxhh.java300.designpattern.state;

public class StateTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		State state = new State();
		Context context = new Context(state);
		
		//设置第一种状态
		state.setValue("state1");
		context.method();
		
		state.setValue("state2");
		context.method();
				
	}

}
