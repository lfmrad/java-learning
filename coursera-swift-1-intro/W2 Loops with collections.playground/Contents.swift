
let levelScores = [10, 20, 30, 40, 50, 60, 70]

for (level, score) in levelScores.enumerated() {
    print("Level \(level+1) - Score: \(score)")
}

var gameScore = 0

for score in levelScores {
    gameScore += score
}
print("Game score: \(gameScore)")

let weeklyTemperatures = ["Monday": 70, "Tuesday": 75, "Wednesday": 80, "Thursday": 85, "Friday": 90, "Saturday": 95, "Sunday": 100]

for (day, temp) in weeklyTemperatures {
    print("Current day: \(day) - Temp: \(temp) ºF")
}

let days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
let temps = [70, 75, 80, 85, 90, 95, 100]

for index in 0...days.count-1 {
    print("Current day: \(days[index]) - Temp: \(temps[index]) ºF")
}
