# thai-segmentation-json
This class is to semi-automatic segment Thai sentences and create a json file from .tsv (tab-separated values) file. 
The output is a JSON file.

The first row of .tsv file is considered as the top column.

## Usage
An input file is needed as a template of a each json object. There are 2 types of structures that this class can handle.

### 1. Object

```
o-NAME-NUMBER
```
meaning
* o = object
* NAME = Name of Object
* NUMBER = range (e.g., `0-5`)or number of column (e.g., `0,1,2,3,4,5`) starting from 0

### 2. Single value

```
s-NUMBER
```
meaning
* s = single
* NUMBER = range (e.g., `0-5`)or number of column (e.g., `0,1,2,3,4,5`) starting from 0

## Example

Suppose we have

id | name | price
--- | --- | ---
5 | ไก่ทอดบอนชอน | 20
6 | ไก่ทอดแสนอร่อย | 25

The command `o-product-1-2` or `o-product-1,2` will return

```
{
  "product": {
    "price": "20",
    "name": "ไก่ทอด บอน ชอน"
  }
}{
  "product": {
    "price": "25",
    "name": "ไก่ทอด แสน อร่อย"
  }
}
```

The command `s-1-2` or `s-1,2` will return

```
{
  "price": "20",
  "name": "ไก่ทอด บอน ชอน"
}{
  "price": "25",
  "name": "ไก่ทอด แสน อร่อย"
}

```
The command 

```
s-0
o-product-1,2
```

will return

```
{
  "product": {
    "price": "20",
    "name": "ไก่ทอด บอน ชอน"
  },
  "id": "5"
}{
  "product": {
    "price": "25",
    "name": "ไก่ทอด แสน อร่อย"
  },
  "id": "6"
}
```

## License
The segmentataion algorithm (namely the class LongLexTo, LongParseTree, Trie and the Lexitron dictionary) belongs to NECTEC under the LGPL License.
