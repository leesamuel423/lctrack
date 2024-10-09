import { InfiniteSlider } from '@/components/core/infinite-slider';

export function InfiniteSliderCompanies() {
  return (
    <>
      <InfiniteSlider gap={30} className="m-4">
        <img
          src='/apple.svg'
          alt='Apple logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/airbnb.svg'
          alt='Airbnb logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/google.svg'
          alt='Google logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/linkedin.svg'
          alt='Linkedin logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/mastercard.svg'
          alt='Mastercard logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/nike.svg'
          alt='Nike logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/paypal.svg'
          alt='Paypal logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/sony.svg'
          alt='Sony logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/tiktok.svg'
          alt='Tiktok logo'
          className='h-[120px] w-auto'
        />
      </InfiniteSlider>
      <h1 className="m-4">Used and Loved By Software Engineers Everywhere</h1>
      <InfiniteSlider gap={30} reverse className="m-4">
        <img
          src='/amazon.svg'
          alt='Amazon logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/oracle.svg'
          alt='Oracle logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/bmw.svg'
          alt='BMW logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/ebay.svg'
          alt='Ebay logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/slack.svg'
          alt='Slack logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/ibm.svg'
          alt='IBM logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/pinterest.svg'
          alt='Pinterest logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/uber.svg'
          alt='Uber logo'
          className='h-[120px] w-auto'
        />
        <img
          src='/soundcloud.svg'
          alt='Soundcloud logo'
          className='h-[120px] w-auto'
        />
      </InfiniteSlider>
    </>
  );
}

