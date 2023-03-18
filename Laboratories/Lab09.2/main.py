from urllib.parse import urlparse
from nltk.corpus import wordnet
import pprint
import numpy as np
import rdflib
import sys
import random


@property
def fragment(self) -> str:
    return urlparse(self).fragment


def getTriplets():
    graph = rdflib.Graph()
    graph.parse("food.rdf")
    triplets = []

    for concept1, relation, concept2 in graph:
        if type(concept1) == rdflib.term.URIRef and type(relation) == rdflib.term.URIRef and type(
                concept2) == rdflib.term.URIRef:
            # print(concept1.fragment, relation.fragment, concept2.fragment)
            triplets.append(concept1.fragment + " " + relation.fragment + " " + concept2.fragment)
    return triplets


def ex3(triplets):
    concepts1 = list()
    concepts2 = list()
    relations = list()

    for triplet in triplets:
        concepts1.append(triplet.split(" ")[0])
        relations.append(triplet.split(" ")[1])
        concepts2.append(triplet.split(" ")[2])

    while True:
        rand_pos = random.randint(0, len(concepts1))
        rand_value = random.randint(1, 3)
        possible_answers = list()

        switcher = {
            1: "Care este Conceptul 1 care are Relatia " + relations[rand_pos] + " si Conceptul 2 " + concepts2[
                rand_pos] + " ?:",
            2: "Care este Conceptul 2 care are Relatia " + relations[rand_pos] + " si Conceptul 1 " + concepts1[
                rand_pos] + " ?:",
            3: "Care este Relatia care are Conceptul 1 " + concepts1[rand_pos] + " si Conceptul 2" + concepts2[
                rand_pos] + " ?:"
        }

        print(switcher.get(rand_value))

        if rand_value == 1:
            for i in range(0, len(concepts1)):
                if concepts1[i] == concepts1[rand_pos] and relations[i] == relations[rand_pos]:
                    possible_answers.append(concepts2[i].lower())

        if rand_value == 2:
            for i in range(0, len(concepts1)):
                if concepts2[i] == concepts2[rand_pos] and relations[i] == relations[rand_pos]:
                    possible_answers.append(concepts1[i].lower())

        if rand_value == 3:
            for i in range(0, len(concepts1)):
                if concepts1[i] == concepts1[rand_pos] and concepts2[i] == concepts2[rand_pos]:
                    possible_answers.append(relations[i].lower())

        print(possible_answers)

        answer = input('Raspuns:')

        if answer.lower() in possible_answers:
            print("Rapuns corect bravo")
            print("Raspunsuri posibile: ", possible_answers)

        try_again = input('Daca vrei sa incerci din nou scrie tasta y, daca nu tasta n:')
        if try_again.lower() != 'y':
            break


def ex4():
    synonyms = list()
    # antonyms = list()
    print(wordnet.synsets(sys.argv[2]))
    print(wordnet.synsets(sys.argv[2])[0].lemmas()[0].name())
    for synset in wordnet.synsets(sys.argv[2]):
        for lemma in synset.lemmas():
            if synonyms.count(lemma.name()) == 0:
                synonyms.append(lemma.name())
            # if lemma.antonyms():
            #    if antonyms.count(lemma.antonyms()[0].name()) == 0:
            #        antonyms.append(lemma.antonyms()[0].name())

    print('Synonyms: ' + str(synonyms))
    # print('Antonyms: ' + str(antonyms))


def ex5(triplets):
    concepts1 = list()
    concepts2 = list()
    relations = list()

    for triplet in triplets:
        concepts1.append(triplet.split(" ")[0])
        relations.append(triplet.split(" ")[1])
        concepts2.append(triplet.split(" ")[2])

    while True:
        rand_pos = random.randint(0, len(concepts1))
        rand_value = random.randint(1, 3)
        possible_answers = list()
        synonyms_list = list()
        hypernyms_list = list()
        meronyms_list = list()

        switcher = {
            1: "Care este Conceptul 1 care are Relatia " + relations[rand_pos] + " si Conceptul 2 " + concepts2[
                rand_pos] + " ?:",
            2: "Care este Conceptul 2 care are Relatia " + relations[rand_pos] + " si Conceptul 1 " + concepts1[
                rand_pos] + " ?:",
            3: "Care este Relatia care are Conceptul 1 " + concepts1[rand_pos] + " si Conceptul 2 " + concepts2[
                rand_pos] + " ?:"
        }

        print(switcher.get(rand_value))

        if rand_value == 1:
            for i in range(0, len(concepts1)):
                if concepts2[i] == concepts2[rand_pos] and relations[i] == relations[rand_pos]:
                    possible_answers.append(concepts1[i].lower())
                    for synset in wordnet.synsets(concepts1[i]):
                        # Synonyms
                        for lemma in synset.lemmas():
                            if lemma.name() not in possible_answers:
                                possible_answers.append(lemma.name())
                                synonyms_list.append(lemma.name())
                            else:
                                continue
                        # Meronyms
                        for meronym in synset.part_meronyms():
                            if meronym.name().split(".")[0] not in meronyms_list:
                                possible_answers.append(meronym.name().split(".")[0])
                                meronyms_list.append(meronym.name().split(".")[0])
                        # Hypernyms
                        for hypernym in synset.hypernyms():
                            if hypernym.name().split(".")[0] not in hypernyms_list:
                                possible_answers.append(hypernym.name().split(".")[0])
                                hypernyms_list.append(hypernym.name().split(".")[0])
                        break

        if rand_value == 2:
            for i in range(0, len(concepts2)):
                if concepts1[i] == concepts1[rand_pos] and relations[i] == relations[rand_pos]:
                    possible_answers.append(concepts2[i].lower())
                    for synset in wordnet.synsets(concepts2[i]):
                        # Synonyms
                        for lemma in synset.lemmas():
                            if lemma.name() not in possible_answers:
                                possible_answers.append(lemma.name())
                                synonyms_list.append(lemma.name())
                            else:
                                continue
                        # Meronyms
                        for meronym in synset.part_meronyms():
                            if meronym.name().split(".")[0] not in meronyms_list:
                                possible_answers.append(meronym.name().split(".")[0])
                                meronyms_list.append(meronym.name().split(".")[0])
                        # Hypernyms
                        for hypernym in synset.hypernyms():
                            if hypernym.name().split(".")[0] not in hypernyms_list:
                                possible_answers.append(hypernym.name().split(".")[0])
                                hypernyms_list.append(hypernym.name().split(".")[0])
                        break

        if rand_value == 3:
            for i in range(0, len(relations)):
                if concepts1[i] == concepts1[rand_pos] and concepts2[i] == concepts2[rand_pos]:
                    possible_answers.append(relations[i].lower())
                    for synset in wordnet.synsets(relations[i]):
                        # Synonyms
                        for lemma in synset.lemmas():
                            if lemma.name() not in possible_answers:
                                possible_answers.append(lemma.name())
                                synonyms_list.append(lemma.name())
                            else:
                                continue
                        # Meronyms
                        for meronym in synset.part_meronyms():
                            if meronym.name().split(".")[0] not in meronyms_list:
                                possible_answers.append(meronym.name().split(".")[0])
                                meronyms_list.append(meronym.name().split(".")[0])
                        # Hypernyms
                        for hypernym in synset.hypernyms():
                            if hypernym.name().split(".")[0] not in hypernyms_list:
                                possible_answers.append(hypernym.name().split(".")[0])
                                hypernyms_list.append(hypernym.name().split(".")[0])
                        break

        print("Raspunsurile posibile sunt: " + str(possible_answers))
        print("Sinonimele sunt: " + str(synonyms_list))
        print("Hypernimele sunt: " + str(hypernyms_list))
        print("Meronimele sunt: " + str(meronyms_list))
        print(" ")
        answer = input('Raspuns : ')

        if answer.lower() in possible_answers:
            print("Rapuns corect bravo")
            if hypernyms_list.count(answer.lower()) > 0:
                print("Ai folosit un hypernym")
            elif meronyms_list.count(answer.lower()) > 0:
                print("Ai folosit un meronym")
            print("Raspunsuri posibile: ", possible_answers)

        try_again = input('Daca vrei sa incerci din nou scrie tasta y, daca nu tasta n:')
        if try_again.lower() != 'y':
            break


if __name__ == "__main__":
    triplets = getTriplets()
    print(" ")
    print("Ex4")
    ex4()
    print(" ")
    print("Ex5")
    ex5(triplets)
    print(" ")

'''
Enunt :  
"
O echipă de cercetare a observat că autobuzele școlare (B) consumă mai multă benzină (G), însă sunt implicate în mai puține accidente (A) decât media. Școlile care folosesc autobuze private sunt: scoli mici (S) și scoli private (C).
"

Probabilitati : 
"
P(S) = 0.30
P(C) = 0.40

|B|P(A)|
|True|0.20|
|False|0.75|

|B|P(G)|
|True|0.80|
|False|0.30|

|S|C|P(A)|
|True|True|0.80|
|True|False|0.60|
|False|True|0.50|
|False|False|0.25|
"

Cerinta:
"
P(A, not B, G)
P(not G, A, B, S, not C)
"

Rezolvare:
"
a)
P(A, not B, G) = Sum_s Sum_c (P(a, not B, G, s, c)

P(A, not B, G, s, c) = P(A|not B) * P(G|not B) * P(B|s,c) * P(s) * P(c) = 0.75 * 0.3 * (1-0.8) * 0.3 * 0.4 = 0.0054
P(A, not B, g, not s, not c) = 0.75 * 0.3 * P(not B | not s, not c) * P(not s) * P(not c) = 0.75 * 0.3 * (1-0.6) * 0.3 * 0.6 = 0.0162
P(A, not B, g, not s, c) = 0.75 * 0.3 * P(not B | not s, c) * P(not s) * P(c) = 0.75 * 0.3 * (1-0.5) * 0.3 * 0.4 = 0.0315
P(A, not B, g, s, not c) = 0.75 * 0.3 * P(not B | s, not c) * P(s) * P(not c) = 0.75 * 0.3 * (1-0.6) * 0.7 * 0.6 = 0.0162

P(A, not B, G) = 0.0054 + 0.0162 + 0.0315 + 0.0162 = 0.0693

P(not G, A, B, S, not C) = P(not G|B) * P(A|B) * P(B|S, not C) * P(S) * P(not C) = (1-0.8) * 0.2 * 0.6 * 0.3 * 0.6 = 0.0043




b)

P(A, not c) = alpha * Sum_s Sum_B Sum_G ( P(s) * P(not c) * P(B|s,not c) * P(G|B) * P(A|B) )

alpha = 1 / P(not c) = 1 / (0.6) = 1.666

P(A, not c, not s, not B, not G) = P(not s) * P(not c) * P(not B| not s, not c) * P(A| not B) * P(not G | not B) = 0.7 *  0.6 * 0.75 * 0.75 * 0.7 = 0.165375
P(A, not c, not s, B, not G) = P(not s) * P(not c) * P(B| not s, not c) * P(A| B) * P(not G | B) = 0.0042
P(A, not c, not s, not B, G) = P(not s) * P(not c) * P(not B| not s, not c) * P(A| not B) * P(G | not B) = 0.0070875
P(A, not c, s, not B, not G) = P(s) * P(not c) * P(not B| s, not c) * P(A| not B) * P(not G | not B) = 0.0378
P(A, not c, s, B, not G) = P(s) * P(not c) * P(B|s, not c) * P(A|B) * P(not G | B) = 0.00648
P(A, not c, s, not B, G) = P(s) * P(not c) * P(not B| s, not c) * P(A| not B) * P(G | not B) = 0.0162 
P(A, not c, not s, B, G) = P(not s) * P(not c) * P(B| not s, not c) * P(A|B) * P(G | B) = 0.0168
P(A, not c, s, B, G) = P(s) * P(c) * P(B|s,c) * P(A|B) * P(G|B) = 0.01728

P(A, not c) = 1.66 * (0.165375+0.0042+0.0070875+0.0378+0.00648+0.0162+0.0168+0.01728) = 1.66 * 0.27 = 0.448

"


'''
