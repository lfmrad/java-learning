var goldBars = 0

func unlockTreasureChest(inventory: Int) -> Int {
    print("You unlocked 1 chest!")
    return inventory + 100
}

print("Gold bars: \(goldBars)")
goldBars = unlockTreasureChest(inventory: goldBars)
print("Gold bars: \(goldBars)")
goldBars = unlockTreasureChest(inventory: goldBars)
print("Gold bars: \(goldBars)")
goldBars = unlockTreasureChest(inventory: goldBars)
print("Gold bars: \(goldBars)")
