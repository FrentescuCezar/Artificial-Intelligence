from urllib.parse import urlparse
from nltk.corpus import wordnet
import numpy as np
import rdflib


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
            print(concept1.fragment, concept2.fragment)
            triplets.append(concept1.fragment + " " + relation.fragment + " " + concept2.fragment)
