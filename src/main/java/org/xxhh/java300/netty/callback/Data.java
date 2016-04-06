package org.xxhh.java300.netty.callback;

public class Data {
	private int n;
	private int m;

	public Data(int n, int m) {
		this.n = n;
		this.m = m;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		int r = n / m;
		return n + "/" + m + "=" + r;
	}

}
