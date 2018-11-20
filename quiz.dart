class Item {
  final int index;
  final String source;
  final String translation;
  const Item(this.index, this.source, this.translation);
  @override
  toString() => "${this.index} - ${this.source} - ${this.translation}";
}

class Quiz3 {
  final Item question;
  final List<Item> answers;
  Quiz3(this.question, this.answers);
  isCorrect(int indx) => this.question.index == indx;
}

class QuizBuilder2 {

  List<Item> items;
  final int number;
  QuizBuilder2(this.items, this.number);
  List<int> randomize() {
   
    var indices = this.items.map((it) => it.index).toList();
    indices.shuffle(new Random());
    print("rnadomizes");
    indices.take(3).forEach(print);
    return indices.take(this.number).toList();
  }

  List<Item> getRandomItems(List<int> arr) => this.items.where((it) => arr.contains(it.index)).toList();
	
	Quiz3 buildQuiz() {
    
    var randomizer = new Random(); 
    var len = items.length;
  	var num = randomizer.nextInt(len);
  	print(num);  
    var question = this.items[num];   
    print ("question: ${this.items[num]}");
    
    this.items.removeAt(num);
    
    var answers   = getRandomItems(randomize());        
    
    var insertPosition = randomizer.nextInt(this.number);
    answers.insert(insertPosition, question);
    
    return  Quiz3(question, answers);
  }  
}


var items = [
  Item(1, "aaa", "AAA"),
  Item(2, "bbb", "BBB"),
  Item(3, "ccc", "CCC"),
  Item(4, "ddd", "DDD"),
  Item(5, "eee", "EEE"),
  Item(6, "fff", "FFF"),
  Item(7, "ggg", "GGG"),
  Item(8, "hhh", "HHH")
];


  var quizBuilder2 = QuizBuilder2(items,3);
  var q3 = quizBuilder2.buildQuiz();
 
  
  q3.answers.forEach((it){
   	print(it); 
    if(q3.isCorrect(it.index))
      	print(" ${it.index}  --- correct");
  });
