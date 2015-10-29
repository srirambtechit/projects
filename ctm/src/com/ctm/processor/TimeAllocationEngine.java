package com.ctm.processor;

import java.util.ArrayList;
import java.util.List;

import com.ctm.beans.Talk;

public class TimeAllocationEngine {

	List<Entry> entries = new ArrayList<>();

	public TimeAllocationEngine(List<Talk> talks) {
		convertTalkToEntry(talks);
	}

	public void process() {

		int[] arr = { 3, 2, 8, 2, 1, 4, 7 };
		int sum = 11;
		

	}

	private void convertTalkToEntry(List<Talk> talks) {
		if (talks != null && !talks.isEmpty()) {
			for (Talk talk : talks) {
				Entry entry = new Entry(talk.getId(), talk.getDuration());
				entries.add(entry);
			}
		}
	}

	private class Entry {
		private int id;
		private int time;

		public Entry(int id, int time) {
			this.id = id;
			this.time = time;
		}

		public int id() {
			return this.id;
		}

		public int time() {
			return this.time;
		}
	}

}
