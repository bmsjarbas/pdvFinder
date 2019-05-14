
Since this is a simple example, i've created manually fake objects instead of 

create 2dsphere index
db.pdv.createIndex({ coverageArea :"2dsphere"});
