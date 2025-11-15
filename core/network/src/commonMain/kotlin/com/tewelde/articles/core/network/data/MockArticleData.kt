package com.tewelde.articles.core.network.data

object MockArticleData {

    // returns a bare JSON array (NetworkArticle[]) because client deserializes body<List<NetworkArticle>>()
    fun getArticleListJson(category: String? = null): String {
        val filteredArticles = if (category.isNullOrBlank()) {
            allArticles
        } else {
            allArticles.filter { article ->
                article.category.equals(category, ignoreCase = true)
            }
        }

        if (filteredArticles.isEmpty()) return "[]"

        val articlesJson = filteredArticles.joinToString(",\n") { article ->
            """
            {
              "id": "${article.id}",
              "title": "${article.title}",
              "summary": "${article.summary}",
              "updatedAt": "${article.updatedAt}",
              "category": "${article.category}"
            }
            """.trimIndent()
        }

        return """
        [
            $articlesJson
        ]
        """.trimIndent()
    }

    fun getArticleDetailJson(id: String): String {
        val article = articlesMap[id] ?: articlesMap["1"]!!
        return """
            {
              "id": "${article.id}",
              "title": "${article.title}",
              "summary": "${article.summary}",
              "category": "${article.category}",
              "content": ${article.content.toJsonString()},
              "updatedAt": "${article.updatedAt}"
            }
        """.trimIndent()
    }

    private data class MockArticle(
        val id: String,
        val title: String,
        val summary: String,
        val content: String,
        val updatedAt: String,
        val category: String
    )

    private val allArticles = listOf(
        MockArticle(
            id = "1",
            title = "How to Book Your Eurail Pass",
            summary = "Step-by-step guide to booking your first Eurail pass online",
            content = """
# How to Book Your Eurail Pass

Booking your Eurail pass is **easy**! Follow these steps:

## Step 1: Choose Your Pass

Decide between:
- **Global Pass** - Travel across 33 countries
- **One Country Pass** - Explore a single country in depth

## Step 2: Select Travel Days

Pick the number of travel days that suit your trip. Options include:

1. Consecutive days (e.g., 15 days in 2 months)
2. Flexible days (e.g., 7 days within 1 month)

## Step 3: Complete Your Order

1. Add to cart
2. Enter passenger details
3. Make payment securely

**Need help?** [Contact our support team](https://eurail.com/support)

---

*Last updated: November 10, 2025*
            """.trimIndent(),
            updatedAt = "2025-11-10T14:30:00Z",
            category = "Booking"
        ),

        MockArticle(
            id = "2",
            title = "Train Reservations Explained",
            summary = "Understanding when and how to make seat reservations",
            content = """
# Train Reservations Explained

Not all trains require reservations, but some **high-speed** and **night trains** do.

## When Are Reservations Required?

### Always Required
- French TGV trains
- Italian Frecciarossa
- Spanish AVE
- Most night trains

### Optional or Not Required
- Regional trains
- Most German ICE trains
- Austrian railjet (except to Italy)

## How to Make Reservations

You can book reservations through:

1. **Online** - Via the Eurail reservation portal
2. **At stations** - Visit ticket counters
3. **By phone** - Call the reservation service

> **Pro tip:** Book popular routes early, especially in summer!

## Costs

Reservation fees typically range from €3 to €35 depending on:
- Train type
- Route distance
- Season

For more details, check our [reservation guide](https://eurail.com/reservations).
            """.trimIndent(),
            updatedAt = "2025-11-08T09:15:00Z",
            category = "Travel Tips"
        ),

        MockArticle(
            id = "3",
            title = "Mobile Pass Activation",
            summary = "How to activate and use your mobile Eurail pass",
            content = """
# Mobile Pass Activation

Your mobile pass makes traveling **paperless** and convenient!

## Before You Travel

### 1. Download the App

Get the **Rail Planner** app:
- iOS: App Store
- Android: Google Play

### 2. Add Your Pass

1. Open the app
2. Tap "Add a new Pass"
3. Enter your pass number and last name

## Activating Your Pass

**Important:** Only activate when you're ready to start traveling!

Steps:
1. Select your pass
2. Tap "Activate pass"
3. Choose your start date
4. Confirm activation

⚠️ **Warning:** Activation cannot be undone!

## Adding Travel Days

For each journey:

1. Search for your train
2. Add it to "My Trip"
3. Slide to activate the travel day

### Before Boarding

Make sure your:
- [ ] Pass is activated
- [ ] Travel day is toggled ON
- [ ] Phone is charged

## Offline Mode

The app works offline, but download timetables before traveling to areas with poor connectivity.

Need technical help? Email support@eurail.com
            """.trimIndent(),
            updatedAt = "2025-11-05T16:45:00Z",
            category = "Mobile Pass"
        ),

        MockArticle(
            id = "4",
            title = "Refund & Exchange Policy",
            summary = "What to do if your travel plans change",
            content = """
# Refund & Exchange Policy

Life happens! Here's what to do if your plans change.

## Before Activation

### Full Refund
If your pass is **not activated**, you can get a full refund minus a cancellation fee:

- €15 for passes under €200
- €30 for passes €200 and above

**Deadline:** Request within 11 months of purchase.

### Exchange
You can exchange for a different pass type or travel dates. Exchange fee applies.

## After Activation

Unfortunately, **activated passes cannot be refunded or exchanged**.

## How to Request

### Online
1. Log into your account
2. Go to "My orders"
3. Select "Request refund"
4. Follow the instructions

### By Email
Contact customer-service@eurail.com with:
- Order number
- Pass number
- Reason for refund

## Processing Time

Refunds are processed within:
- 5-7 business days (online requests)
- 10-14 business days (email requests)

## Special Circumstances

In case of:
- Medical emergencies
- Force majeure events

Contact our support team to discuss options on a case-by-case basis.

---

Questions? Check our [full terms & conditions](https://eurail.com/terms).
            """.trimIndent(),
            updatedAt = "2025-11-01T11:20:00Z",
            category = "Refunds"
        ),

        MockArticle(
            id = "5",
            title = "Night Train Guide",
            summary = "Everything you need to know about traveling on night trains",
            content = """
# Night Train Guide

Save time and accommodation costs by traveling **overnight**!

## Types of Accommodation

### 1. Seated
- Standard seats
- Most affordable option
- Limited comfort for sleeping

### 2. Couchette (4-6 berths)
- Shared compartment
- Basic bunks
- Pillow and blanket provided

### 3. Sleeper (1-3 berths)
- Private or semi-private
- Beds with proper mattresses
- Sink and toiletries included

## Popular Night Train Routes

| Route | Duration | Starting from |
|-------|----------|---------------|
| Paris → Rome | 14h | €29 |
| Vienna → Venice | 11h | €19 |
| Berlin → Stockholm | 16h | €34 |

## What's Included

Most night trains offer:
- ✔ Bedding
- ✔ Electrical outlets
- ✔ Luggage storage
- ✔ Toilet facilities

Some premium services include:
- Breakfast
- Private shower
- Welcome drink

## Booking Tips

1. **Book early** - Sleeper compartments sell out fast
2. **Consider gender-specific** compartments for solo travelers
3. **Check reservation fees** - Usually €20-€40
4. **Bring earplugs** - Not all passengers sleep quietly!

## Safety

Night trains are generally safe, but:
- Keep valuables close
- Lock your compartment door
- Use luggage straps if in couchettes

**Sweet dreams and happy travels!** 🌙🚂
            """.trimIndent(),
            updatedAt = "2025-10-28T08:00:00Z",
            category = "Travel Tips"
        ),

        MockArticle(
            id = "6",
            title = "First Class vs Second Class",
            summary = "Comparing travel classes to help you choose the right option",
            content = """
# First Class vs Second Class

Wondering which class to book? Here's a detailed comparison.

## Seating

### Second Class
- Standard comfortable seats
- 2+2 or 3+2 configuration
- Adequate legroom

### First Class
- Wider, more luxurious seats
- 2+1 or 2+2 configuration
- Extra legroom and recline

## Amenities

| Feature | Second Class | First Class |
|---------|--------------|-------------|
| WiFi | Usually free | Always free |
| Power outlets | Sometimes | Always |
| Quiet zones | Limited | More common |
| Complimentary drinks | No | Often included |

## Crowds

First class is typically **less crowded**, meaning:
- More personal space
- Easier to find seats
- Quieter environment

## Price Difference

First class passes cost approximately **50% more** than second class.

## Who Should Choose First Class?

Consider first class if you:
- Value extra comfort
- Need to work during travel
- Prefer quieter environments
- Are traveling long distances frequently

## Who Should Choose Second Class?

Second class is great if you:
- Are budget-conscious
- Don't mind occasional crowds
- Are traveling shorter distances
- Want to meet more travelers

## Upgrading

Some trains allow **on-the-spot upgrades** for a fee. Check with train staff.

---

**Bottom line:** Both classes get you to your destination. Choose based on your comfort needs and budget!
            """.trimIndent(),
            updatedAt = "2025-10-25T13:00:00Z",
            category = "Travel Tips"
        ),

        MockArticle(
            id = "7",
            title = "Scenic Train Routes in Europe",
            summary = "The most breathtaking railway journeys across the continent",
            content = """
# Scenic Train Routes in Europe

Experience Europe's natural beauty from your train window! 🏔️

## Top 10 Scenic Routes

### 1. Glacier Express (Switzerland)
**Zermatt to St. Moritz**
- Duration: 8 hours
- Highlights: 291 bridges, 91 tunnels, Alpine landscapes
- Best season: Year-round

### 2. Bernina Express (Switzerland/Italy)
**Chur to Tirano**
- Duration: 4 hours
- Highlights: UNESCO World Heritage route, glaciers
- Must-see: Landwasser Viaduct

### 3. Bergen Railway (Norway)
**Oslo to Bergen**
- Duration: 7 hours
- Highlights: Mountain plateaus, fjords
- Peak views: Finse station area

### 4. West Highland Line (Scotland)
**Glasgow to Mallaig**
- Duration: 5.5 hours
- Famous for: Glenfinnan Viaduct (Harry Potter!)
- Scenery: Lochs, moors, mountains

### 5. Semmering Railway (Austria)
**Vienna region**
- First mountain railway in Europe
- UNESCO World Heritage Site
- Engineering marvel

## Tips for Scenic Journeys

### Seat Selection
- Book window seats early
- Check which side has best views
- Consider seat direction

### Photography
- Clean your window before departure
- Turn off flash
- Use burst mode for moving scenery

### Planning
- Travel during daylight hours
- Check weather forecasts
- Consider slower regional trains

## Passes That Cover These Routes

Most scenic routes are **included** in your Eurail pass!

Exceptions requiring supplements:
- Glacier Express: Panoramic car reservation
- Bernina Express: Panoramic car reservation

## Best Time to Travel

| Season | Advantages |
|--------|-----------|
| Spring | Flowers, waterfalls |
| Summer | Clear skies, long days |
| Autumn | Fall colors |
| Winter | Snow-covered landscapes |

**Pro tip:** Midweek trains are less crowded for better photo opportunities!
            """.trimIndent(),
            updatedAt = "2025-10-20T10:30:00Z",
            category = "Destinations"
        ),

        MockArticle(
            id = "8",
            title = "Traveling with Luggage",
            summary = "Tips for managing your bags on European trains",
            content = """
# Traveling with Luggage

Pack smart and travel light! Here's how to manage your belongings.

## Luggage Allowance

Good news: **No strict weight limits** on most European trains!

However, you must be able to:
- Carry your own bags
- Store them yourself
- Manage them on/off trains

## Storage Options

### Overhead Racks
- For smaller bags
- Keep within sight
- Standard on all trains

### Floor Space
- Between seats
- Under seats (limited)
- Near doors (risky!)

### Dedicated Areas
- End of carriages
- Near entrances
- First-come, first-served

## Packing Recommendations

### Ideal Bag Size
- Backpack: 40-50L
- Suitcase: Cabin size (55x40x20cm)
- Easy to maneuver

### What NOT to Bring
- Oversized suitcases
- Multiple large bags
- Heavy, awkward items

## Security Tips

1. **Use locks** on all zippers
2. **Keep valuables** in carry-on
3. **Stay alert** at stations
4. **Label bags** with contact info
5. **Consider insurance** for expensive items

## Left Luggage Services

Most major stations offer:
- Lockers (€4-8 per day)
- Staffed storage (€6-12 per day)
- 24-hour availability

### Popular Stations with Storage

- Paris Gare du Nord
- Amsterdam Centraal
- Rome Termini
- Barcelona Sants
- Munich Hauptbahnhof

## Bike Transport

Traveling with a bicycle?
- Check train policies
- Reserve bike space
- Folding bikes are easier
- Some trains have bike cars

---

**Remember:** A lighter bag means a happier journey! 🎒
            """.trimIndent(),
            updatedAt = "2025-10-15T14:45:00Z",
            category = "Travel Tips"
        ),

        MockArticle(
            id = "9",
            title = "Youth Discount Pass Guide",
            summary = "Special rates and benefits for travelers under 28",
            content = """
# Youth Discount Pass Guide

Are you under 28? Save big on your European adventure! 🎉

## Eligibility

### Age Requirements
- Must be **27 or younger** on first travel day
- Age verified at purchase
- ID required when traveling

## Savings

Youth passes offer approximately **25% discount** compared to adult passes!

### Example Savings

| Pass Type | Adult Price | Youth Price | You Save |
|-----------|-------------|-------------|----------|
| Global 7 days | €326 | €251 | €75 |
| Global 15 days | €441 | €341 | €100 |
| Global 1 month | €558 | €431 | €127 |

## What's Included

Youth passes include **all the same benefits**:
- Unlimited train travel
- Access to all countries
- Mobile pass option
- Same validity period

## Proof of Age

Accepted documents:
- Passport
- National ID card
- Driver's license

**Important:** Always carry ID when traveling!

## Combining Discounts

### Group Travel
- Travel with friends
- Split costs on reservations
- Share experiences

### Student Cards
- Some countries offer additional discounts
- Check local benefits
- ISIC cards widely accepted

## Tips for Young Travelers

1. **Book hostels** near train stations
2. **Use night trains** to save on accommodation
3. **Travel off-peak** for fewer crowds
4. **Connect with others** using travel apps
5. **Be flexible** with your itinerary

## Popular Youth Routes

- Interrailing classics
- Festival hopping
- University city tours
- Beach destinations

---

**Your adventure awaits!** Make memories while saving money. 🌍✨
            """.trimIndent(),
            updatedAt = "2025-10-10T09:00:00Z",
            category = "Booking"
        ),

        MockArticle(
            id = "10",
            title = "Senior Traveler Benefits",
            summary = "Discounts and tips for travelers 60 and older",
            content = """
# Senior Traveler Benefits

Experience Europe at your own pace with special senior benefits! 👴👵

## Age Eligibility

### Qualification
- **60 years or older** on first travel day
- Valid ID required
- Automatic discount applied

## Discount Structure

Senior passes offer approximately **10% discount**:

| Pass Type | Adult Price | Senior Price | Savings |
|-----------|-------------|--------------|---------|
| Global 7 days | €326 | €293 | €33 |
| Global 15 days | €441 | €397 | €44 |
| Global 1 month | €558 | €502 | €56 |

## Comfort Recommendations

### First Class Advantages
- More spacious seating
- Quieter environment
- Better amenities
- Easier boarding

### Seat Selection Tips
- Book aisle seats for mobility
- Choose near toilets if needed
- Request assistance at stations
- Avoid climbing stairs to upper decks

## Accessibility Services

Many European trains offer:
- Wheelchair access
- Priority boarding
- Staff assistance
- Accessible toilets

### Requesting Help

1. Contact station **48 hours ahead**
2. Arrive **30 minutes early**
3. Look for assistance points
4. Use station staff

## Health Considerations

### Travel Insurance
- Strongly recommended
- Cover medical expenses
- Include trip cancellation
- Check medication coverage

### Medication
- Carry prescriptions
- Keep in original packaging
- Bring enough supply
- Store in carry-on bag

## Recommended Itineraries

### Relaxed Pace Tours
- 2-3 cities maximum per week
- Include rest days
- Choose central hotels
- Plan morning travels

### Popular Senior Routes
- Rhine Valley (Germany)
- Cinque Terre (Italy)
- Loire Valley (France)
- Swiss Alps

## Station Facilities

Most major stations have:
- Elevators and escalators
- Seating areas
- Clean restrooms
- Dining options
- Medical assistance

---

**Travel is timeless!** Enjoy Europe at a pace that suits you. 🚆🌻
            """.trimIndent(),
            updatedAt = "2025-10-05T11:15:00Z",
            category = "Booking"
        ),

        MockArticle(
            id = "11",
            title = "Border Crossings & Passport Control",
            summary = "What to expect when crossing borders by train",
            content = """
# Border Crossings & Passport Control

Crossing borders by train is usually **seamless**, but here's what to know.

## Schengen Area

### What is Schengen?
26 European countries with **no internal border controls**:
- No passport checks between countries
- Free movement
- Single visa for all

### Schengen Countries
Austria, Belgium, Czech Republic, Denmark, Estonia, Finland, France, Germany, Greece, Hungary, Iceland, Italy, Latvia, Liechtenstein, Lithuania, Luxembourg, Malta, Netherlands, Norway, Poland, Portugal, Slovakia, Slovenia, Spain, Sweden, Switzerland

## Non-Schengen Travel

### Countries with Border Checks
- United Kingdom
- Ireland
- Romania
- Bulgaria
- Croatia (joining soon)

### What to Expect

1. **Document check** - Passport/ID required
2. **Brief stop** - Usually 15-30 minutes
3. **Customs** - Random bag checks possible
4. **Stamps** - Passport may be stamped

## Required Documents

### EU Citizens
- National ID card OR
- Valid passport

### Non-EU Citizens
- Valid passport
- Schengen visa (if required)
- Proof of return travel
- Accommodation details

## Popular Border Crossings

| Route | Border Type | Check? |
|-------|-------------|--------|
| France → Germany | Schengen | No |
| France → UK (Eurostar) | Non-Schengen | Yes |
| Austria → Switzerland | Schengen | No |
| Germany → Poland | Schengen | No |

## Eurostar Special Case

Traveling to/from UK:
- Check-in **30 minutes early**
- Full passport control
- Security screening
- Similar to airport process

## Tips for Smooth Crossings

1. **Keep documents accessible**
2. **Know your train's route**
3. **Be aware of time zones**
4. **Carry copies of important docs**
5. **Have accommodation proof ready**

## What If Checks Occur?

Even in Schengen, random checks may happen:
- Stay calm
- Present documents
- Answer questions honestly
- Be patient

---

**Bon voyage!** Borders shouldn't slow down your adventure! 🛂🌍
            """.trimIndent(),
            updatedAt = "2025-09-28T15:30:00Z",
            category = "Travel Tips"
        ),

        MockArticle(
            id = "12",
            title = "Family Travel with Children",
            summary = "Tips and discounts for traveling with kids on trains",
            content = """
# Family Travel with Children

Train travel with kids can be **fun and stress-free**! Here's how.

## Child Discounts

### Free Travel
Children **under 4** travel FREE:
- No ticket needed
- No seat guaranteed
- Sit on parent's lap

### Discounted Travel
Children **4-11 years**:
- Reduced price passes
- About 50% discount
- Own seat guaranteed

### Youth Rates
Ages **12-27**:
- Youth discount applies
- Must have own pass
- ID required

## Family Pass Options

### What's Included
- 2 adults + children
- Flexible travel days
- Same benefits as individual passes

### Cost Savings
Families typically save **15-20%** with family passes.

## On-Board Amenities

### Family-Friendly Features
- Family compartments
- Changing tables
- Play areas (some trains)
- Kids' menus in dining cars

### What to Bring
- Snacks and drinks
- Entertainment (books, tablets)
- Comfort items
- Change of clothes
- Wet wipes

## Stroller Management

### Tips for Strollers
1. Use **compact, foldable** strollers
2. Check storage space availability
3. Board early for best spots
4. Consider baby carriers instead

### Storage Locations
- Between carriages
- Designated areas
- Near your seat (folded)

## Keeping Kids Entertained

### Age-Appropriate Activities

**Toddlers (2-4)**
- Picture books
- Stickers
- Soft toys
- Snacks

**Young Children (5-8)**
- Coloring books
- Travel games
- Window watching
- Simple card games

**Older Children (9-12)**
- Books or e-readers
- Tablets with headphones
- Travel journals
- Board games

## Practical Tips

1. **Choose direct trains** when possible
2. **Travel during nap times**
3. **Book seats together**
4. **Prepare for delays**
5. **Take breaks** at interesting stations

## Safety First

- Hold hands on platforms
- Watch the gap
- Supervise at all times
- Know emergency procedures
- Carry first aid kit

---

**Happy family travels!** Making memories one train ride at a time! 👨‍👩‍👧‍👦🚂
            """.trimIndent(),
            updatedAt = "2025-09-20T12:00:00Z",
            category = "Travel Tips"
        ),

        MockArticle(
            id = "13",
            title = "Understanding Timetables",
            summary = "How to read and use European train schedules",
            content = """
# Understanding Timetables

Master the art of reading train schedules! 📅

## Where to Find Timetables

### Official Sources
- **Rail Planner App** (recommended)
- National railway websites
- Station information boards
- Printed pocket schedules

### The Rail Planner App

Your best friend for planning:
- Offline access
- Real-time updates
- Save favorite routes
- Filter by pass validity

## Reading a Timetable

### Key Information

| Symbol | Meaning |
|--------|---------|
| 🚄 | High-speed train |
| 🚂 | Regional train |
| ℹ️ | Information |
| ⚠️ | Alert/Warning |
| ♿ | Accessible |
| 🍽️ | Restaurant car |

### Time Format
Europe uses **24-hour clock**:
- 08:00 = 8 AM
- 14:30 = 2:30 PM
- 20:45 = 8:45 PM

## Platform Information

### Station Boards
- **Yellow** = Departures
- **White** = Arrivals
- Updated in real-time

### Platform Numbering
- May change last minute
- Check boards frequently
- Listen to announcements
- Allow buffer time

## Connection Planning

### Transfer Times

**Recommended minimums:**
- Same station: 15-20 minutes
- Different stations: 45-60 minutes
- With luggage: Add 10 minutes

### Backup Plans
- Note alternative trains
- Check next departure
- Know station layout
- Have offline maps ready

## Peak vs Off-Peak

### Peak Hours
- Morning: 7:00-9:00
- Evening: 17:00-19:00
- More crowded
- May need reservations

### Off-Peak Benefits
- Less crowded
- Better seat choice
- Sometimes cheaper supplements
- More relaxed travel

## Seasonal Changes

### Summer Schedule (June-September)
- More frequent trains
- Extended hours
- Tourist routes added
- Book ahead!

### Winter Schedule (October-May)
- Reduced frequency
- Earlier last trains
- Check for delays
- Weather impacts

## Common Abbreviations

| Code | Meaning |
|------|---------|
| Hbf | Hauptbahnhof (Main Station) |
| Gare | French station |
| Stazione | Italian station |
| IC | InterCity |
| EC | EuroCity |

---

**Plan smart, travel easy!** The timetable is your roadmap to adventure! 🗺️⏰
            """.trimIndent(),
            updatedAt = "2025-09-15T08:45:00Z",
            category = "Planning"
        ),

        MockArticle(
            id = "14",
            title = "WiFi and Connectivity on Trains",
            summary = "Staying connected while traveling across Europe",
            content = """
# WiFi and Connectivity on Trains

Stay connected during your journey! 📶

## WiFi Availability

### High-Speed Trains
Most offer **free WiFi**:
- TGV (France)
- ICE (Germany)
- Frecciarossa (Italy)
- Thalys
- Eurostar

### Regional Trains
WiFi availability varies:
- Less common
- May be slower
- Sometimes paid

## How to Connect

### Typical Process
1. Select train's network
2. Accept terms & conditions
3. Enter email (sometimes)
4. Start browsing

### Common Network Names
- WIFI@DB (Germany)
- TGV WiFi (France)
- Eurostar WiFi
- _Frecciarossa_WiFi

## Connection Quality

### What to Expect
- Good for emails, messaging
- Decent for browsing
- Variable for streaming
- May drop in tunnels

### Speed Factors
- Number of users
- Train location
- Technology age
- Weather conditions

## Mobile Data Options

### EU Roaming
EU residents enjoy:
- **Free roaming** in EU
- Use home data plan
- No extra charges
- Check fair use policy

### Non-EU Travelers
Consider:
- Local SIM cards
- International roaming
- Portable WiFi devices
- Offline downloads

## Power Outlets

### Availability
| Train Type | Outlets |
|------------|---------|
| High-speed | Most seats |
| Regional | Limited |
| Night trains | Each berth |
| Older trains | Few/none |

### Adapter Tips
- Bring universal adapter
- European plugs vary
- USB outlets common
- Carry power bank

## Offline Preparation

### Download Before Travel
- Maps (Google Maps, MAPS.ME)
- Timetables (Rail Planner)
- Entertainment (Netflix, Spotify)
- Translation apps
- Boarding passes

### Why Go Offline?
- Tunnels block signal
- Saves battery
- No data worries
- Always accessible

## Work-Friendly Features

### First Class Advantages
- Better WiFi
- More outlets
- Quieter carriages
- Tables for laptops

### Productivity Tips
1. Charge devices fully beforehand
2. Download key files
3. Use airplane mode to save battery
4. Bring noise-canceling headphones

## Entertainment

### Streaming
- Pre-download content
- Lower quality uses less data
- Some trains offer onboard media

### Gaming
- Offline games recommended
- Low latency not guaranteed
- Mobile games work best

---

**Stay connected or unplug!** The choice is yours. 💻🔌
            """.trimIndent(),
            updatedAt = "2025-09-10T16:20:00Z",
            category = "Travel Tips"
        ),

        MockArticle(
            id = "15",
            title = "Emergency Contacts & Safety",
            summary = "Important numbers and safety information for train travelers",
            content = """
# Emergency Contacts & Safety

Your safety is our priority. Know what to do! 🚨

## Emergency Numbers

### Universal EU Number
**112** - Works everywhere in Europe
- Police
- Fire
- Medical
- Free from any phone

### Country-Specific

| Country | Police | Medical |
|---------|--------|---------|
| France | 17 | 15 |
| Germany | 110 | 112 |
| Italy | 113 | 118 |
| Spain | 091 | 061 |
| UK | 999 | 999 |

## On-Train Emergencies

### Emergency Brake
- Located in every carriage
- Use ONLY for genuine emergencies
- Fine for misuse
- Will stop train immediately

### Staff Assistance
- Find conductor
- Use intercom systems
- Look for staff carriages
- Alert button in toilets

## Medical Emergencies

### On Board
1. Alert train staff immediately
2. Ask for medical professionals
3. Basic first aid available
4. Trains can stop at stations

### Be Prepared
- Carry personal medications
- Have insurance info handy
- Know your blood type
- List allergies on phone

## Theft Prevention

### Pickpocket Hotspots
- Busy stations
- Boarding/alighting
- Tourist areas
- Night trains

### Protection Tips
1. Use **money belts**
2. Keep bags **closed and secured**
3. Don't flash valuables
4. Stay alert in crowds
5. Lock compartments at night

## Lost Property

### On the Train
- Report immediately to staff
- Note train number & seat
- Check lost and found cars

### At Stations
- Visit lost property office
- File report same day
- Provide detailed description
- Check online databases

## Travel Insurance

### Highly Recommended Coverage
- Medical expenses
- Trip cancellation
- Lost/stolen belongings
- Travel delays
- Emergency evacuation

### Popular Providers
- World Nomads
- Allianz Travel
- Safety Wing
- AXA Assistance

## Natural Disasters

### Staying Informed
- Monitor local news
- Sign up for alerts
- Embassy registration
- Rail company updates

### What to Do
- Follow crew instructions
- Stay calm
- Know exit locations
- Have emergency supplies

## Important Documents

### Keep Copies Of
- Passport
- Rail pass
- Insurance policy
- Credit cards
- Emergency contacts

### Storage Tips
- Digital copies in email
- Cloud storage
- Different physical locations
- Travel companion has copies

## Embassy Information

### When to Contact
- Lost/stolen passport
- Legal troubles
- Medical emergencies abroad
- Natural disasters
- Political unrest

### Find Your Embassy
Visit your country's foreign affairs website for complete listing of embassies across Europe.

---

**Stay safe and enjoy your travels!** Preparation brings peace of mind. 🛡️🚂
            """.trimIndent(),
            updatedAt = "2025-09-05T11:00:00Z",
            category = "Safety"
        )
    )

    private val articlesMap = allArticles.associateBy { it.id }

    // Helper to escape JSON strings properly
    private fun String.toJsonString(): String {
        return buildString {
            append('"')
            this@toJsonString.forEach { char ->
                when (char) {
                    '"' -> append("\\\"")
                    '\\' -> append("\\\\")
                    '\n' -> append("\\n")
                    '\r' -> append("\\r")
                    '\t' -> append("\\t")
                    else -> append(char)
                }
            }
            append('"')
        }
    }
}