package search;

import java.util.ArrayList;

public class ThreadedSearch<T> implements Runnable {

  private T target;
  private ArrayList<T> list;
  private int begin;
  private int end;
  private Answer answer;

  public ThreadedSearch() {
  }

  private ThreadedSearch(T target, ArrayList<T> list, int begin, int end, Answer answer) {
    this.target=target;
    this.list=list;
    this.begin=begin;
    this.end=end;
    this.answer=answer;
  }

  /**
  * Searches `list` in parallel using `numThreads` threads.
  *
  * You can assume that the list size is divisible by `numThreads`
  */
  public boolean parSearch(int numThreads, T target, ArrayList<T> list) throws InterruptedException {
    /*
    * First construct an instance of the `Answer` inner class. This will
    * be how the threads you're about to create will "communicate". They
    * will all have access to this one shared instance of `Answer`, where
    * they can update the `answer` field inside that instance.
    *
    * Then construct `numThreads` instances of this class (`ThreadedSearch`)
    * using the 5 argument constructor for the class. You'll hand each of
    * them the same `target`, `list`, and `answer`. What will be different
    * about each instance is their `begin` and `end` values, which you'll
    * use to give each instance the "job" of searching a different segment
    * of the list. If, for example, the list has length 100 and you have
    * 4 threads, you would give the four threads the ranges [0, 25), [25, 50),
    * [50, 75), and [75, 100) as their sections to search.
    *
    * You then construct `numThreads`, each of which is given a different
    * instance of this class as its `Runnable`. Then start each of those
    * threads, wait for them to all terminate, and then return the answer
    * in the shared `Answer` instance.
    */
	  //initiating variables CombAnswer, Start, Finsih, and dist
	  //Starting new thread threader
	  Answer CombAnswers = new Answer();
	  Thread[] theader = new Thread[numbThreads];
	  int Start = 0, Finish = 0;
	  int dist = list.size();

	  //for each thread in threader, setting Finish equal to Start + dist
	  for(int i = 0; i < numThreads; i++){
	  	Finish = Start + dist;
		//starting a new thread for every thread in threader
		threader[i] = new Thread(new ThreadedSearch<T>(target, list, Start, Finish, CombAnswers));
		threader[i].start();
		Start = Finish;
	  }

	  //joining each thread in threader
	  
	  for(int i = 0; i < numThreads; ++i){
	  threader[i].join();
	  }

	  //return the final answer
    return CombAnswers.getAnswer();
  }

  //running operation for each thread in threader
  public void run() {
	for(int i = Start; i <= Finish; i++){
		if(list.get(i).equals(target)){
			CombAnswers.setAnswer(true);
			break;
		}
	}
  }


  private class Answer {
    private boolean answer = false;

    public boolean getAnswer() {
      return answer;
    }

    // This has to be synchronized to ensure that no two threads modify
    // this at the same time, possibly causing race conditions.
    public synchronized void setAnswer(boolean newAnswer) {
      answer = newAnswer;
    }
  }

}
