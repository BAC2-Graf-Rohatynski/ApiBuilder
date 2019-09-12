package apibuilder.randomgenerator

import apibuilder.randomgenerator.interfaces.IRandomGenerator

object RandomGenerator: IRandomGenerator {
    override fun bigRandom(): Int = (10000..19999).shuffled().first()
}