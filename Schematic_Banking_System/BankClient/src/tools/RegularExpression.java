package tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression {
    private Pattern pattern;
    private Matcher matcher;

    public String a;

    public static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
    /*
    ^                     # Start of the line
    [a-z0-9_-]	          # Match characters and symbols in the list, a-z, 0-9, underscore, hyphen
    {3,15}                # Length at least 3 characters and maximum length of 15
    $                     # End of the line
    */
    public static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    /*
        (		    	    # Start of group
        (?=.*\d)		    #   must contains one digit from 0-9
        (?=.*[a-z])	    	#   must contains one lowercase characters
        (?=.*[A-Z])	    	#   must contains one uppercase characters
        (?=.*[@#$%])		#   must contains one special symbols in the list "@#$%"
        .		            #     match anything with previous condition checking
        {6,20}	            #        length at least 6 characters and maximum of 20
        )		        	# End of group
     */


    public static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    /*
        ^		        #start of the line
        (	        	#  start of group #1
        [01]?\\d\\d?    #    Can be one or two digits. If three digits appear, it must start either 0 or 1
                        #    e.g ([0-9], [0-9][0-9],[0-1][0-9][0-9])
        |		        #    ...or
        2[0-4]\\d	    #    start with 2, follow by 0-4 and end with any digit (2[0-4][0-9])
        |               #    ...or
        25[0-5]         #    start with 2, follow by 5 and ends with 0-5 (25[0-5])
        )		        #  end of group #2
        \.              #  follow by a dot "."
        ....            # repeat with 3 times (3x)
        $		        #end of the line
     */
    public static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    /*
        ^		        	#start of the line
        [_A-Za-z0-9-\\+]+	#  must start with string in the bracket [ ], must contains one or more (+)
        (			        #   start of group #1
        \\.[_A-Za-z0-9-]+	#     follow by a dot "." and string in the bracket [ ], must contains one or more (+)
        )*		        	#   end of group #1, this group is optional (*)
        @		        	#     must contains a "@" symbol
        [A-Za-z0-9-]+       #       follow by string in the bracket [ ], must contains one or more (+)
        (		        	#         start of group #2 - first level TLD checking
        \\.[A-Za-z0-9]+     #           follow by a dot "." and string in the bracket [ ], must contains one or more (+)
        )*		            #         end of group #2, this group is optional (*)
        (			        #         start of group #3 - second level TLD checking
        \\.[A-Za-z]{2,}     #           follow by a dot "." and string in the bracket [ ], with minimum length of 2
        )			        #         end of group #3
        $			        #end of the line
     */
    public static final String TIME12HOURS_PATTERN =
            "(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
    /*
            (			    	#start of group #1
            1[012]				#  start with 10, 11, 12
            |			    	#  or
            [1-9]				#  start with 1,2,...9
            )			    	#end of group #1
            :			    	#    follow by a semi colon (:)
            [0-5][0-9]			#      follw by 0..5 and 0..9, which means 00 to 59
            (\\s)?		        #        follow by a white space (optional)
            (?i)	        	#          next checking is case insensitive
            (am|pm)	            #            follow by am or pm
     */
    public static final String TIME24HOURS_PATTERN =
            "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    /*
        (				    #start of group #1
        [01]?[0-9]			#  start with 0-9,1-9,00-09,10-19
        |			    	#  or
        2[0-3]				#  start with 20-23
        )			    	#end of group #1
        :				    #    follow by a semi colon (:)
        [0-5][0-9]			#      follw by 0..5 and 0..9, which means 00 to 59
     */
    public static final String DATE_PATTERN =
            "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
    /*
    (		        	#start of group #1
    0?[1-9]		        #  01-09 or 1-9
    |                  	#  ..or
    [12][0-9]		    #  10-19 or 20-29
    |			        #  ..or
    3[01]		    	#  30, 31
    ) 		        	#end of group #1
    /		        	#  follow by a "/"
    (		        	#    start of group #2
    0?[1-9]		        #	01-09 or 1-9
    |			        #	..or
    1[012]		        #	10,11,12
    )			        #    end of group #2
    /			        #	follow by a "/"
    (			        #	  start of group #3
    (19|20)\\d\\d	    #	    19[0-9][0-9] or 20[0-9][0-9]
    )		            #	  end of group #3
    */


    public boolean validate(final String text, final String patternName) {

        pattern = Pattern.compile(patternName);
        matcher = pattern.matcher(text);
        return matcher.matches();

    }

    public boolean isNumber(String str) {
        try {
            float f = Float.parseFloat(str);
            return true;
        } catch (Exception e) {
            return false;

        }
    }


}
