// game.js

class Game {
  constructor() {
    this.players = [];
    this.running = false;
  }

  addPlayer(player) {
    this.players.push(player);
  }

  start() {
    this.running = true;
    console.log('Game started!');
    this.gameLoop();
  }

  stop() {
    this.running = false;
    console.log('Game stopped!');
  }

  gameLoop() {
    if (!this.running) return;

    console.log('Game loop running...');
    // Update game mechanics here

    setTimeout(() => this.gameLoop(), 1000 / 60); // 60 FPS
  }
}

class Player {
  constructor(name) {
    this.name = name;
    this.controls = {
      up: false,
      down: false,
      left: false,
      right: false,
    };
  }

  move() {
    if (this.controls.up) console.log(`${this.name} moves up`);
    if (this.controls.down) console.log(`${this.name} moves down`);
    if (this.controls.left) console.log(`${this.name} moves left`);
    if (this.controls.right) console.log(`${this.name} moves right`);
  }
}

// Example usage
const game = new Game();
const player1 = new Player('Player1');
const player2 = new Player('Player2');

game.addPlayer(player1);
game.addPlayer(player2);

game.start();

setTimeout(() => game.stop(), 5000); // Stop game after 5 seconds