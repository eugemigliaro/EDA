public class ExactSearch {


    public static int indexOf(char[] query, char[] target)
    {
        int idxTarget= 0;
        int idxQuery= 0;

        while(idxTarget < target.length &&  idxQuery < query.length)  {
            if (query[idxQuery] == target[idxTarget])  {
                idxQuery++;
                idxTarget++;
            }
            else  {
                idxTarget= idxTarget - idxQuery + 1;
                idxQuery = 0;
            }
        }

        if (idxQuery == query.length) // found!
            return idxTarget-idxQuery;
        return -1;
    }

    public static int indexOfv2(char[] query, char[] target){
        int idxTarget= 0;
        int idxQuery = 0;

        while(idxTarget < target.length &&  idxQuery < query.length) {
            if (query[idxQuery] == target[idxTarget])   {
                idxQuery++;
                idxTarget++;
                if (idxQuery == query.length)
                    return idxTarget-idxQuery;
            }
            else  {
                idxTarget= idxTarget - idxQuery + 1;
                idxQuery = 0;
            }
        }
        return -1;
    }


}
