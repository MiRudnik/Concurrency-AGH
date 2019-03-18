
var Fork = function() {
  this.state = 0;
  return this;
}

Fork.prototype.acquire = function(id, cb) { 
  
  var delay = 1;
  var fork = this;
  var BEB = function() {
    //console.log("%s,%s",id, delay);
    if(fork.state == 0){
      console.log("[",id,"] Took fork, delay = ", delay);
      fork.state = 1;
      if(cb) cb();
    }
    else{
      delay = delay*2;
      setTimeout(BEB, delay);
    }
  }
  
  setTimeout(BEB, delay);
  
}

Fork.prototype.release = function() { 
  this.state = 0; 
}

var conductor;

var Conductor = function(allowed) {
  this.state = allowed;
  return this;
}

Conductor.prototype.acquire = function(id, cb) { 
  
  var delay = 1;
  var BEB = function() {
    //console.log("%s,%s",id, delay);
    if(conductor.state != 0){
      console.log("[",id,"] Allowed to eat, delay = ", delay);
      conductor.state--;
      if(cb) cb();
    }
    else{
      delay = delay*2;
      setTimeout(BEB, delay);
    }
  }
  
  setTimeout(BEB, delay);
  
}


Conductor.prototype.release = function() { 
  this.state++; 
}

var Philosopher = function(id, forks) {
  this.id = id;
  this.forks = forks;
  this.f1 = id % forks.length;
  this.f2 = (id+1) % forks.length;
  return this;
}

Philosopher.prototype.startNaive = function(count) {
  var forks = this.forks,
      f1 = this.f1,
      f2 = this.f2,
      id = this.id;
  
  // zaimplementuj rozwiazanie naiwne

  for(var i=0; i<count; i++){
    
    forks[f1].acquire(id, function() {
      forks[f2].acquire(id, function() {
        console.log("[",id,"] Started eating");
        forks[f1].release();
        forks[f2].release();
        console.log("[",id,"] Finished eating");
      });
    }); 

  }

}

Philosopher.prototype.startAsym = function(count) {
  var forks = this.forks,
      f1 = this.f1,
      f2 = this.f2,
      id = this.id;
  
  // zaimplementuj rozwiazanie asymetryczne

  var phil = this;

  if(id%2 == 0){
    var first = f2;
    var second = f1;
  }
  else{
    var first = f1;
    var second = f2;
  }

  if(count > 0){
    
    forks[first].acquire(id, function() {
      forks[second].acquire(id, function() {
        console.log("[",id,"] Started eating");
        forks[first].release();
        forks[second].release();
        console.log("[",id,"] Finished eating");
        phil.startAsym(count-1);
      });
    }); 

  }

}

Philosopher.prototype.startConductor = function(count) {
  var forks = this.forks,
      f1 = this.f1,
      f2 = this.f2,
      id = this.id;
  
  // zaimplementuj rozwiazanie z kelnerem

  var phil = this;
  
  if(count > 0){

    conductor.acquire(id, function() {
      forks[f1].acquire(id, function() {
        forks[f2].acquire(id, function() {
          console.log("[",id,"] Started eating");
          forks[f1].release();
          forks[f2].release();
          console.log("[",id,"] Finished eating");
          conductor.release();
          phil.startConductor(count-1);
        });
      });
    });
  
  }

}


var N = 5;
var forks = [];
var philosophers = [];
conductor = new Conductor(N-1);
for (var i = 0; i < N; i++) {
  forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
  philosophers.push(new Philosopher(i, forks));
}

/*
for (var i = 0; i < N; i++) {
  philosophers[i].startNaive(10);
}
*/

/*
for (var i = 0; i < N; i++) {
  philosophers[i].startAsym(10);
}
*/


for (var i = 0; i < N; i++){
  philosophers[i].startConductor(10);
}
