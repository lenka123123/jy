package sinia.com.baihangeducation.club.editorclubactive.model;


import java.util.List;

public class GetClubActiveOption {


    public List<activeType> type_list;
    public List<activeClub> club_list;

    public static class activeType {
        public String type;
        public String type_name;
    }

    public static class activeClub {
        public String club_id;
        public String club_name;
    }


}

