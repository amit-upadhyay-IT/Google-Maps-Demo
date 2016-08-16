Location Fetching through LocationManager
The location is coming as latitude and longitude in  double form. So this is not much use for us. We've used reverse Geocoding
to get the location into String form.

#Near by location
Places near by us can also be shown using Reverse Geocoding. EG:
List<Address> addrsList = geocoder.getFromLocation(latitude, longitude, 2);// when we write 2 it means we are requesting only 2 addresses nearest to my latitude and longitude, so by writing something like :
List<Address> addrsList = geocoder.getFromLocation(latitude, longitude, 10); // we can fetch 10 nearby locations and then we can use this loop :

                for(int i = 0; i < address.getMaxAddressLineIndex(); ++i)
                {
                    builder.append(address.getAddressLine(i)+"\n");
                }
To get the ith address. eg : String adrs5 = address.getAddressLine(5);
