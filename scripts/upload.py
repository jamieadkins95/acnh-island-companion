#!/usr/bin/python3
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import json

# Use a service account
cred = credentials.Certificate('scripts/service-account.json')
firebase_admin.initialize_app(cred)

db = firestore.client()
batch = db.batch()

fish_ref = db.collection(u'fish')
with open('scripts/fish.json') as json_file:
    data = json.load(json_file)
    for fish in data:
        doc_ref = fish_ref.document(fish['name'].lower())
        batch.set(doc_ref, fish)


bugs_ref = db.collection(u'bugs')
with open('scripts/bugs.json') as json_file:
    data = json.load(json_file)
    for bug in data:
        doc_ref = bugs_ref.document(bug['name'].lower())
        batch.set(doc_ref, bug)

batch.commit()
